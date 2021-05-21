package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "genre1";

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        int actualGenresCount = genreDao.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(2, "genre2");
        genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectDeleteGenreById() {
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXISTING_GENRE_ID);

        assertThatThrownBy(() -> genreDao.getById(EXISTING_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre);
    }

}