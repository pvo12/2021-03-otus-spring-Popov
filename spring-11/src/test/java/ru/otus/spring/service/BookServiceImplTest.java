package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.BookCommentRepositoryJpa;
import ru.otus.spring.repositories.BookRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис книг должен ")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    BookRepositoryJpa bookRepositoryJpa;
    @Mock
    BookCommentRepositoryJpa bookCommentRepositoryJpa;
    @Mock
    GenreService genreService;
    @Mock
    AuthorService authorService;
    @InjectMocks
    private BookServiceImpl bookService;
    private final long EXISTING_BOOK_ID = 1;
    private final String EXISTING_BOOK_TITLE = "book1";
    private final String EXPECTING_BOOK_TITLE = "book2";
    private final String EXISTING_AUTHOR_FIO = "author1";
    private final Author EXISTING_AUTHOR = new Author(1, EXISTING_AUTHOR_FIO);
    private final String EXISTING_GENRE_NAME = "genre1";
    private final Genre EXISTING_GENRE = new Genre(1, EXISTING_GENRE_NAME);
    private final String EXPECTING_AUTHOR_FIO = "author2";
    private final String EXPECTING_GENRE_NAME = "genre2";
    Genre EXPECTING_GENRE = new Genre(3, EXPECTING_GENRE_NAME);
    Author EXPECTING_AUTHOR = new Author(4, EXPECTING_AUTHOR_FIO);

    @Test
    @DisplayName("удалять книгу по переданному id")
    void shouldDeleteBook() {
        bookService.delete(EXISTING_BOOK_ID);
        Mockito.verify(bookRepositoryJpa, Mockito.times(1)).deleteById(EXISTING_BOOK_ID);
        Mockito.verify(bookCommentRepositoryJpa, Mockito.times(1)).deleteByBookId(EXISTING_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать книгу по переданному id")
    void shouldGetBook() {
        Book book = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        given(bookRepositoryJpa.findById(EXISTING_BOOK_ID)).willReturn(Optional.of(book));
        var book2 = bookService.getById(EXISTING_BOOK_ID);
        assertThat(book2).isEqualTo(book);
    }

    @Test
    @DisplayName("возвращать список всех книг")
    void shouldGetAllBook() {
        Book book = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        given(bookRepositoryJpa.findAll()).willReturn(List.of(book));
        var books = bookService.getAll();
        assertThat(books).isEqualTo(List.of(book));
    }

    @Test
    @DisplayName("добавлять новую книгу")
    void shouldExecuteInsert() {
        given(genreService.findOrCreateByName(EXISTING_GENRE_NAME)).willReturn(EXISTING_GENRE);
        given(authorService.findOrCreateByFio(EXISTING_AUTHOR_FIO)).willReturn(EXISTING_AUTHOR);

        Book book = new Book(EXISTING_AUTHOR, EXISTING_GENRE, EXPECTING_BOOK_TITLE);
        Book expectedBook = new Book(2, EXISTING_AUTHOR, EXISTING_GENRE, EXPECTING_BOOK_TITLE);

        given(bookRepositoryJpa.findByExample(book)).willReturn(List.of());
        given(bookRepositoryJpa.save(book)).willReturn(expectedBook);

        var id = bookService.insert(EXPECTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);

        assertThat(id).isEqualTo(2);

        Mockito.verify(bookRepositoryJpa, Mockito.times(1)).save(book);
    }

    @Test
    @DisplayName("падать с ошибкой при не переданных параметрах книги")
    void shouldFailWhenEmptyTitle() {
        Assertions.assertThrows(BookServiceException.class, () -> {
            bookService.insert("", EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BookServiceException.class, () -> {
            bookService.insert(EXISTING_BOOK_TITLE, "", EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BookServiceException.class, () -> {
            bookService.insert(EXISTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, "");
        });
        Assertions.assertThrows(BookServiceException.class, () -> {
            bookService.update(EXISTING_BOOK_ID, "", EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BookServiceException.class, () -> {
            bookService.update(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, "", EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BookServiceException.class, () -> {
            bookService.update(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, "");
        });
    }

    @Test
    @DisplayName("возвращать id существующей книги")
    void insert() {
        given(genreService.findOrCreateByName(EXISTING_GENRE_NAME)).willReturn(EXISTING_GENRE);
        given(authorService.findOrCreateByFio(EXISTING_AUTHOR_FIO)).willReturn(EXISTING_AUTHOR);

        Book book1 = new Book(EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        Book book2 = new Book(EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        book2.setId(EXISTING_BOOK_ID);
        given(bookRepositoryJpa.findByExample(book1)).willReturn(List.of(book2));

        var id = bookService.insert(EXISTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);
        assertThat(id).isEqualTo(EXISTING_BOOK_ID);

        Mockito.verify(bookRepositoryJpa, Mockito.times(0)).save(any());
    }

    @Test
    @DisplayName("изменять параметры книги")
    void shouldUpdateBook() {
        Book book = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR, EXISTING_GENRE, EXISTING_BOOK_TITLE);
        given(bookRepositoryJpa.findById(EXISTING_BOOK_ID)).willReturn(Optional.of(book));

        given(genreService.findOrCreateByName(EXPECTING_GENRE_NAME)).willReturn(EXPECTING_GENRE);
        given(authorService.findOrCreateByFio(EXPECTING_AUTHOR_FIO)).willReturn(EXPECTING_AUTHOR);

        bookService.update(EXISTING_BOOK_ID, EXPECTING_BOOK_TITLE, EXPECTING_AUTHOR_FIO, EXPECTING_GENRE_NAME);

        Book book2 = new Book(EXISTING_BOOK_ID, EXPECTING_AUTHOR, EXPECTING_GENRE, EXPECTING_BOOK_TITLE);

        Mockito.verify(bookRepositoryJpa).save(book2);
    }
}