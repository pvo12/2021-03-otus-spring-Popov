package ru.otus.spring.service;

import ru.otus.spring.domain.BookComment;

import java.util.List;

public interface BookCommentService {
    List<BookComment> getAll();
}
