package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.BookBrief;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами должен")
@DataJpaTest
@Import(BookViewRepositoryJpa.class)
class BookBriefRepositoryJpaTest {
    private static final int EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_TITLE = "book1";
    @Autowired
    private BookViewRepositoryJpa repositoryJpa;

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        BookBrief expectedBook = new BookBrief(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE);
        BookBrief actualBook = repositoryJpa.findById(EXISTING_BOOK_ID).orElseThrow();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}