package ru.otus.spring.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.BookComment;

@AllArgsConstructor
@Data
public class BookCommentDto {
    private Long id;
    private String bookTitle;
    private String comment;

    public static BookCommentDto toDto(BookComment bookComment) {
        return new BookCommentDto(bookComment.getId(), bookComment.getBook().getTitle(), bookComment.getComment());
    }
}
