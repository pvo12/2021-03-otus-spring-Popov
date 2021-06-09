package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами должен")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
    private static final int EXPECTED_BOOKS_COUNT = 1;
    private static final int EXISTING_BOOK_ID = 1;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_BOOK_TITLE = "book1";
    private static final Author EXISTING_AUTHOR = new Author(EXISTING_AUTHOR_ID, "author1");
    private static final Genre EXISTING_GENRE = new Genre(EXISTING_GENRE_ID, "genre1");
    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(0, EXISTING_AUTHOR, EXISTING_GENRE, "book2");
        repositoryJpa.save(expectedBook);
        assertThat(expectedBook.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, expectedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        Book actualBook = repositoryJpa.findById(EXISTING_BOOK_ID).orElseThrow();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void shouldCorrectDeleteBookById() {

        assertThat(repositoryJpa.findById(EXISTING_BOOK_ID).isPresent());

        repositoryJpa.deleteById(EXISTING_BOOK_ID);

        assertThat(repositoryJpa.findById(EXISTING_BOOK_ID).isEmpty());
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        var actualBookList = repositoryJpa.findAll();
        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @DisplayName("возвращать список книг по заданному примеру")
    @Test
    void shouldReturnExpectedBooksListByExample() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        var actualBookList = repositoryJpa.findByExample(expectedBook);
        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

}