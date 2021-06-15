package ru.otus.spring.repositories;

import ru.otus.spring.domain.BookBrief;

import java.util.Optional;

public interface BookBriefRepository {
    Optional<BookBrief> findById(long id);
}
