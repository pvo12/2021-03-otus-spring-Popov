package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookMessage {
    private String title;
    private String author;
    private String genre;
}
