package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(
                    resultSet.getLong("id"),
                    authorDao.getById(resultSet.getLong("author_id")),
                    genreDao.getById(resultSet.getLong("genre_id")),
                    resultSet.getString("title")
            );
        }
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(
                Map.of("author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId(),
                        "title", book.getTitle()
                ));

        jdbc.update("insert into books (author_id, genre_id, title) values (:author_id, :genre_id, :title)",
                params, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public void update(Book book) {
        jdbc.update("update books set title = :title where id = :id",
                Map.of("id", book.getId(), "title", book.getTitle()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject(
                "select id, author_id, genre_id, title from books where id = :id", Map.of("id", id), new BookMapper()
        );
    }

    @Override
    public List<Book> getByExample(Book book) {
        return jdbc.query(
                "select id, author_id, genre_id, title from books where title = :title and author_id = :author_id and genre_id = :genre_id",
                Map.of("title", book.getTitle(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()),
                new BookDaoJdbc.BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select * from books", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }
}
