package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий для работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpaImpl.class)
class GenreRepositoryJpaImplTest {
    private static final String EXISTING_GENRE_NAME = "genre1";
    private static final Long EXISTING_GENRE_ID = 1L;
    @Autowired
    private GenreRepositoryJpaImpl repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен добавлять жанр в БД")
    @Test
    void shouldSave() {
        Genre genre = new Genre(0, "author2");
        Genre expectedGenre = repositoryJpa.save(genre);
        assertThat(expectedGenre).usingRecursiveComparison().isEqualTo(genre);
        assertThat(expectedGenre.getId()).isGreaterThan(0);

        Genre actualGenre = em.find(Genre.class, expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName(" должен находить существующий жанр по его названию")
    @Test
    void shouldFindByName() {
        val existingGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        List<Genre> genres = repositoryJpa.findByName(EXISTING_GENRE_NAME);
        assertThat(genres).containsOnlyOnce(existingGenre);
    }
}