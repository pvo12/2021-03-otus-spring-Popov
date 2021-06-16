package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName(value = "Контроллер книг долежн")
class BookControllerTest {
    public static final String ERROR_STRING = "Book not found";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

//    @Autowired
//    private BookService service;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "book1";
    private static final String EXISTING_AUTHOR_TITLE = "author1";
    private static final Author EXISTING_AUTHOR = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_TITLE);
    private static final Genre EXISTING_GENRE = new Genre(EXISTING_GENRE_ID, "genre1");
    private static final Book EXISTING_BOOK = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);

    @Test
    @DisplayName("возвращать корректный список книг")
    void shouldReturnCorrectBooksList() throws Exception {
        List<Book> books = List.of(EXISTING_BOOK, new Book(2L, EXISTING_AUTHOR, EXISTING_GENRE, "book2"));

        List<BookDto> expectedResult = books.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("возвращать корректный список комментариев по id книги")
    void shouldReturnCorrectBookCommentList() throws Exception {
        List<BookCommentDto> expectedResult = List.of(new BookCommentDto(1L, "book2", "comment1"));

        mvc.perform(get("/book/1/comment"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("возвращать книгу по id")
    void shouldReturnCorrectPersonById() throws Exception {
        BookDto expectedResult = BookDto.toDto(EXISTING_BOOK);

        mvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("возвращать ожидаемую ошибку когда книга не найдена")
    void shouldReturnExpectedErrorWhenBookNotFound() throws Exception {
        mvc.perform(get("/book/3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));
    }

    @Test
    @DisplayName("добавлять книгу и удалять книгу по id")
    void shouldAddAndDeleteBookById() throws Exception {
        BookDto bookDto = new BookDto(3L, "author", "genre", "title3");
        mvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk());

        mvc.perform(get("/book/3"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));


        mvc.perform(delete("/book/3"))
                .andExpect(status().isOk());

        mvc.perform(get("/book/3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));
    }

    @Test
    @DisplayName("изменять книгу по id")
    void shouldDeleteBookById() throws Exception {
        BookDto bookDto = BookDto.toDto(EXISTING_BOOK);
        bookDto.setAuthor("author2");

        mvc.perform(put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk());

        mvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));

        bookDto.setAuthor(EXISTING_AUTHOR_TITLE);

        mvc.perform(put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk());
    }
}