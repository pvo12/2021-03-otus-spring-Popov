package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.repositories.BookCommentRepository;
import ru.otus.spring.repositories.BookViewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {
    private final BookCommentRepository repository;
    private final BookViewRepository bookViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public long insert(long book_id, String comment) {
        var book = bookViewRepository.findById(book_id).orElseThrow();
        var bookComment = new BookComment(0, book, comment);
        return repository.save(bookComment).getId();
    }
}
