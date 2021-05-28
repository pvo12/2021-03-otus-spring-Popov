package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    long insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    List<Genre> getByName(String name);
}
