package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from genres", Integer.class);
    }

    @Override
    public long insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(
                Map.of("name", genre.getName()));

        jdbc.update("insert into genres (`name`) values (:name)",
                params, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject(
                "select * from genres where id = :id", Map.of("id", id), new GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genres", new GenreMapper());
    }

    @Override
    public List<Genre> getByName(String name) {
        return jdbc.query(
                "select * from genres where name = :name", Map.of("name", name), new GenreMapper()
        );
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }
}
