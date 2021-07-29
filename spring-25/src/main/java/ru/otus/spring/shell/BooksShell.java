package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.BookService;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BooksShell {
    private final BookService service;

    @ShellMethod(value = "Get all books", key = "getAll")
    public List<Book> getBook() {
        return service.getAll();
    }

}

