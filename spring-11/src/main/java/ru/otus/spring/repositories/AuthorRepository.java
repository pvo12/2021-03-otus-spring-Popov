package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface AuthorRepository {
    Author save(Author author);
    List<Author> findByFio(String fio);
}
