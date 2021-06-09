package ru.otus.spring.repositories;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookView;

import java.util.List;
import java.util.Optional;

public interface BookViewRepository {
    Optional<BookView> findById(long id);
}
