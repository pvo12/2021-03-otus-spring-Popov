package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 1;
    private static final int EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_NAME = "book1";
    public static final int EXISTING_BOOK_AUTHOR_ID = 1;
    public static final int EXISTINT_BOOK_GENRE_ID = 1;

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int actualBooksCount = bookDao.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(2, 1, 1, "book2");
        bookDao.insert(expectedBook);
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_AUTHOR_ID, EXISTINT_BOOK_GENRE_ID, EXISTING_BOOK_NAME);
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_AUTHOR_ID, EXISTINT_BOOK_GENRE_ID, EXISTING_BOOK_NAME);
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

}