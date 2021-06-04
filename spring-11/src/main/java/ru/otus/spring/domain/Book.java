package ru.otus.spring.domain;

import lombok.*;

@AllArgsConstructor
@Data
public class Book {
    private long id;
    private Author author;
    private Genre genre;
    private String title;

    public Book(Author author, Genre genre, String title) {
        this.author = author;
        this.genre = genre;
        this.title = title;
    }
}
