package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"), resultSet.getString("fio"));
        }
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from authors", Integer.class);
    }

    @Override
    public long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(
                Map.of("fio", author.getFio()));

        jdbc.update("insert into authors (`fio`) values (:fio)",
                params, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject(
                "select * from authors where id = :id", Map.of("id", id), new AuthorMapper()
        );
    }

    @Override
    public List<Author> getByFio(String fio) {
        return jdbc.query(
                "select * from authors where fio = :fio", Map.of("fio", fio), new AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

}
