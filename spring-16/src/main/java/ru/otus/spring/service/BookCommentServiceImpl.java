package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.repositories.BookBriefRepository;
import ru.otus.spring.repositories.BookCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {
    private final BookCommentRepository repository;
    private final BookBriefRepository bookBriefRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getAllCommentsByBookID(long bookId) {
        return repository.findAllByBookId(bookId);
    }

    @Override
    @Transactional
    public long insert(long book_id, String comment) {
        var book = bookBriefRepository.findById(book_id).orElseThrow();
        var bookComment = new BookComment(0, book, comment);
        return repository.save(bookComment).getId();
    }
}
