package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.config.JwtConfiguration;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.TokenServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName(value = "Контроллер книг должен")
class BookControllerTest {
    private static final String ERROR_STRING = "Book not found";
    private String token;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    Authentication authentication;

    @Spy
    @Autowired
    JwtConfiguration jwtConfiguration;

    @InjectMocks
    private TokenServiceImpl tokenService;

/*    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;*/

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "book1";
    private static final String EXISTING_AUTHOR_TITLE = "author1";
    private static final Author EXISTING_AUTHOR = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_TITLE);
    private static final Genre EXISTING_GENRE = new Genre(EXISTING_GENRE_ID, "genre1");
    private static final Book EXISTING_BOOK = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);

    @BeforeEach
    void setUp() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("User");
        Collection authorities = Arrays.asList(authority);
        Mockito.when(authentication.getAuthorities()).thenReturn(authorities);
        Mockito.when(authentication.getName()).thenReturn("user");
/*        Mockito.when(jwtConfiguration.getPrivateKey()).thenReturn(privateKey);
        Mockito.when(jwtConfiguration.getPublicKey()).thenReturn(publicKey);*/
        token = tokenService.token(authentication);
    }

    @Test
    @DisplayName("возвращать корректный список книг")
    void shouldReturnCorrectBooksList() throws Exception {
        List<Book> books = List.of(EXISTING_BOOK, new Book(2L, EXISTING_AUTHOR, EXISTING_GENRE, "book2"));

        List<BookDto> expectedResult = books.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/api/book")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("возвращать корректный список комментариев по id книги")
    void shouldReturnCorrectBookCommentList() throws Exception {
        List<BookCommentDto> expectedResult = List.of(
                new BookCommentDto(1L, "book2", "comment1"),
                new BookCommentDto(2L, "book2", "comment2"));

        mvc.perform(get("/api/book/2/comment")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("возвращать книгу по id")
    void shouldReturnCorrectPersonById() throws Exception {
        BookDto expectedResult = BookDto.toDto(EXISTING_BOOK);

        mvc.perform(get("/api/book/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("возвращать ожидаемую ошибку когда книга не найдена")
    void shouldReturnExpectedErrorWhenBookNotFound() throws Exception {
        mvc.perform(get("/api/book/3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));
    }

    @Test
    @DisplayName("добавлять книгу")
    void shouldAddBook() throws Exception {
        BookDto bookDto = new BookDto(3L, "author", "genre", "title3");
        mvc.perform(post("/api/book")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk());

        mvc.perform(get("/api/book/3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    @DisplayName("удалять книгу по id")
    void shouldDeleteBookById() throws Exception {
        mvc.perform(get("/api/book/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BookDto.toDto(EXISTING_BOOK))));

        mvc.perform(delete("/api/book/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mvc.perform(get("/api/book/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));
    }

    @Test
    @DisplayName("изменять книгу по id")
    void shouldPutBookById() throws Exception {
        BookDto bookDto = BookDto.toDto(EXISTING_BOOK);
        bookDto.setAuthor("author2");

        mvc.perform(put("/api/book/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk());

        mvc.perform(get("/api/book/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }
}