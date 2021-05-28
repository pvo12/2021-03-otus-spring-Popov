package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    long insert(Author author);

    Author getById(long id);

    List<Author> getByFio(String fio);

    List<Author> getAll();
}
