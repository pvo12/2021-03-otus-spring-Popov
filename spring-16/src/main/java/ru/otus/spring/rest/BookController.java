package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.service.BookCommentService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.BookServiceException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService service;
    private final BookCommentService bookCommentService;

    @GetMapping("/book")
    public List<BookDto> getAll() {
        return service.getAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookDto getById(@PathVariable("id") long id) {
        return BookDto.toDto(service.getById(id));
    }

    @GetMapping("/book/{id}/comment")
    public List<BookCommentDto> getAllComments() {
        return bookCommentService.getAll().stream()
                .map(BookCommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/book")
    public BookDto create(@RequestBody BookDto book) {
        book.setId(service.insert(book.getTitle(), book.getAuthor(), book.getGenre()));
        return book;
    }

    @PutMapping("/book")
    public BookDto editBook(@RequestBody BookDto book) {
        service.update(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre());
        return book;
    }

    @DeleteMapping("/book/{id}")
    public void deleteById(@PathVariable("id") long id) {
        service.delete(id);
    }


    @ExceptionHandler(BookServiceException.class)
    public ResponseEntity<String> handleBookNotFound(BookServiceException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
