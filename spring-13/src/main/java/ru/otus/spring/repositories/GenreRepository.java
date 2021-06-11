package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreRepository {
    Genre save(Genre genre);
    List<Genre> findByName(String name);
}
