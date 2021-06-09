package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.repositories.BookCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService{
    private final BookCommentRepository repository;

    @Override
    public List<BookComment> getAll() {
        return repository.findAll();
    }
}
