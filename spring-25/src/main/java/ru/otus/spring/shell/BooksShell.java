package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
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

    @ShellMethod(value = "Get book -id", key = "get")
    public Book getBook(
            @ShellOption(value = "-id") long id
    ) {
        return service.getById(id);
    }

    @ShellMethod(value = "Add book -title -author -genre", key = "add")
    public void addBook(
        @ShellOption(value = "-title") String title,
        @ShellOption(value = "-author") String author,
        @ShellOption(value = "-genre") String genre
    ) {
        service.add(title, author, genre);
    }

}

