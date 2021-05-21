package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    long insert(Book book);

    void update(Book book);

    Book getById(long id);

    List<Book> getByParams(Book book);

    List<Book> getAll();

    void deleteById(long id);
}
