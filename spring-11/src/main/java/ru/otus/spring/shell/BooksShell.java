package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.service.BookCommentService;
import ru.otus.spring.service.BookService;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BooksShell {
    private final BookService service;
    private final BookCommentService bookCommentService;

    @ShellMethod(value = "Add book -title -author -genre", key = "add")
    public String addBook(
            @ShellOption(value = "-title") String title,
            @ShellOption(value = "-author") String author,
            @ShellOption(value = "-genre") String genre
    ) {
        return "Book added id=" + service.insert(title, author, genre);
    }

    @ShellMethod(value = "Update book -id -title -author -genre", key = "update")
    public void updateBook(
            @ShellOption(value = "-id") long id,
            @ShellOption(value = "-title") String title,
            @ShellOption(value = "-author") String author,
            @ShellOption(value = "-genre") String genre
    ) {
        service.update(id, title, author, genre);
    }

    @ShellMethod(value = "Delete book -id", key = "delete")
    public void updateBook(
            @ShellOption(value = "-id") long id
    ) {
        service.delete(id);
    }

    @ShellMethod(value = "Get book -id", key = "get")
    public Book getBook(
            @ShellOption(value = "-id") long id
    ) {
        return service.getById(id);
    }

    @ShellMethod(value = "Get all books", key = "getAll")
    public List<Book> getBook() {
        return service.getAll();
    }

    @ShellMethod(value = "Get all book comments", key = "getAllComments")
    public List<BookComment> getBookComments() {
        return bookCommentService.getAll();
    }

}

