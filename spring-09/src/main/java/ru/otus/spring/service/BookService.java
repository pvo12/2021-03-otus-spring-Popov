package ru.otus.spring.service;

import lombok.NonNull;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
    void delete(long id);

    Book getById(long id);

    List<Book> getAll();

    long insert(String bookTitle, String authorFio, String genreName);

    void update(long id, String bookTitle, String authorFio, String genreName);
}
