package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами ")
@DataJpaTest
class AuthorRepositoryTest {
    private static final String EXISTING_AUTHOR_FIO = "author1";
    private static final Long EXISTING_AUTHOR_ID = 1L;
    @Autowired
    private AuthorRepository repository;
    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен добавлять автора в БД")
    @Test
    void shouldSave() {
        Author author = new Author(0, "author2");
        Author expectedAuthor = repository.save(author);
        assertThat(expectedAuthor).usingRecursiveComparison().isEqualTo(author);
        assertThat(expectedAuthor.getId()).isGreaterThan(0);

        Author actualAuthor = em.find(Author.class, expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName(" должен находить существующего автора по его фио")
    @Test
    void shouldFindByFio() {
        val existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        List<Author> authors = repository.findByFio(EXISTING_AUTHOR_FIO);
        assertThat(authors).containsOnlyOnce(existingAuthor);
    }
}