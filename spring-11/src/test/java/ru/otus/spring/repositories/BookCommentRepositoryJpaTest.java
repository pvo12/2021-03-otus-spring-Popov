package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями книг ")
@DataJpaTest
@Import(BookCommentRepositoryJpa.class)
class BookCommentRepositoryJpaTest {
    private static final int EXISTING_BOOK_ID = 1;
    private static final int EXISTING_BOOK_COMMENT_ID = 1;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_BOOK_TITLE = "book1";
    private static final Author EXISTING_AUTHOR = new Author(EXISTING_AUTHOR_ID, "author1");
    private static final Genre EXISTING_GENRE = new Genre(EXISTING_GENRE_ID, "genre1");
    private static final Book EXISTING_BOOK = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
    private static final BookComment EXISTING_BOOK_COMMENT = new BookComment(EXISTING_BOOK_COMMENT_ID, EXISTING_BOOK, "comment1");

    @Autowired
    private BookCommentRepositoryJpa repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен добавлять комментарий в БД")
    void shouldSave() {
        BookComment bookComment = new BookComment(0, EXISTING_BOOK, "comment");
        BookComment expectedBookComment = repositoryJpa.save(bookComment);
        assertThat(expectedBookComment).usingRecursiveComparison().isEqualTo(bookComment);
        assertThat(expectedBookComment.getId()).isGreaterThan(0);

        BookComment actualBookComment = em.find(BookComment.class, expectedBookComment.getId());
        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @Test
    @DisplayName("должен возвращать ожидаемый комментарий по его id")
    void findById() {
        BookComment actualBookComment = repositoryJpa.findById(EXISTING_BOOK_COMMENT_ID).orElseThrow();
        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_COMMENT);
    }

    @Test
    @DisplayName("должен возвращать ожидаемый список всех комментариев")
    void findAll() {
        var actualBookCommentList = repositoryJpa.findAll();
        assertThat(actualBookCommentList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_BOOK_COMMENT);
    }

    @Test
    @DisplayName("удалять комментарий по его id")
    void deleteById() {
        assertThat(repositoryJpa.findById(EXISTING_BOOK_COMMENT_ID).isPresent());
        repositoryJpa.deleteById(EXISTING_BOOK_COMMENT_ID);
        assertThat(repositoryJpa.findById(EXISTING_BOOK_COMMENT_ID).isEmpty());
    }
}