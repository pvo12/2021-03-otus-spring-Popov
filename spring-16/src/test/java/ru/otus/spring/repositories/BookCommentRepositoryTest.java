package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.BookBrief;
import ru.otus.spring.domain.BookComment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями книг ")
@DataJpaTest
class BookCommentRepositoryTest {
    private static final Long EXISTING_BOOK_ID = 2L;
    private static final Long EXISTING_BOOK_COMMENT_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "book2";
    private static final BookBrief EXISTING_BOOK = new BookBrief(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE);
    private static final BookComment EXISTING_BOOK_COMMENT = new BookComment(EXISTING_BOOK_COMMENT_ID, EXISTING_BOOK, "comment1");

    @Autowired
    private BookCommentRepository repository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен добавлять комментарий в БД")
    void shouldSave() {
        BookComment bookComment = new BookComment(0, EXISTING_BOOK, "comment");
        BookComment expectedBookComment = repository.save(bookComment);
        assertThat(expectedBookComment).usingRecursiveComparison().isEqualTo(bookComment);
        assertThat(expectedBookComment.getId()).isGreaterThan(0);

        BookComment actualBookComment = em.find(BookComment.class, expectedBookComment.getId());
        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @Test
    @DisplayName("должен возвращать ожидаемый комментарий по его id")
    void findById() {
        BookComment actualBookComment = repository.findById(EXISTING_BOOK_COMMENT_ID).orElseThrow();
        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_COMMENT);
    }

    @Test
    @DisplayName("должен возвращать ожидаемый список всех комментариев")
    void findAll() {
        var actualBookCommentList = repository.findAll();
        assertThat(actualBookCommentList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_BOOK_COMMENT);
    }

    @Test
    @DisplayName("удалять комментарий по его id")
    void deleteById() {
        assertThat(repository.findById(EXISTING_BOOK_COMMENT_ID).isPresent());
        repository.deleteById(EXISTING_BOOK_COMMENT_ID);
        assertThat(repository.findById(EXISTING_BOOK_COMMENT_ID).isEmpty());
    }
}