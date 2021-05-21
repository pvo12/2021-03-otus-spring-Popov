package ru.otus.spring.domain;

import lombok.*;

@AllArgsConstructor
@Data
public class Book {
    private long id;
    private long authorId;
    private long genreId;
    private String title;

    public Book(long authorId, long genreId, String title) {
        this.authorId = authorId;
        this.genreId = genreId;
        this.title = title;
    }
}
