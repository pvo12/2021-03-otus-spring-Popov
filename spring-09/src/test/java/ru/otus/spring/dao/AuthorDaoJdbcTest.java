package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_NAME = "author1";

    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int actualAuthorsCount = authorDao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(2, "author2");
        authorDao.insert(expectedAuthor);
        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTING_AUTHOR_ID);

        assertThatThrownBy(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedAuthor);
    }
}