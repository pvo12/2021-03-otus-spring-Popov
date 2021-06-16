package ru.otus.spring.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Book;

@AllArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String author;
    private String genre;
    private String title;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getAuthor().getFio(), book.getGenre().getName(), book.getTitle());
    }
}
