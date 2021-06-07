package ru.otus.spring.repositories;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Book save(Book book);
    Optional<Book> findById(long id);
    List<Book> findAll();
    List<Book> findByExample(Book book);
    void deleteById(long id);
}
