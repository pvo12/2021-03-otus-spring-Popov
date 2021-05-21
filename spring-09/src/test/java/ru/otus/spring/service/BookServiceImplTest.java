package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис книг должен ")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    BookDao bookDao;
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
    private final String EXISTING_GENRE_NAME = "genre1";
    private final String EXPECTING_AUTHOR_FIO = "author2";
    private final String EXPECTING_GENRE_NAME = "genre2";
    private final long EXISTING_AUTHOR_ID = 1;
    private final long EXISTING_GENRE_ID = 1;
    private final long EXPECTING_AUTHOR_ID = 3;
    private final long EXPECTING_GENRE_ID = 4;

    @Test
    @DisplayName("удалять книгу по переданному id")
    void shouldDeleteBook() {
        bookService.delete(EXISTING_BOOK_ID);
        Mockito.verify(bookDao, Mockito.times(1)).deleteById(EXISTING_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать книгу по переданному id")
    void shouldGetBook() {
        Book book = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_TITLE);
        given(bookDao.getById(EXISTING_BOOK_ID)).willReturn(book);
        var book2 = bookService.getById(EXISTING_BOOK_ID);
        assertThat(book2).isEqualTo(book);
    }

    @Test
    @DisplayName("возвращать список всех книг")
    void shouldGetAllBook() {
        Book book = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_TITLE);
        given(bookDao.getAll()).willReturn(List.of(book));
        var books = bookService.getAll();
        assertThat(books).isEqualTo(List.of(book));
    }

    @Test
    @DisplayName("добавлять новую книгу")
    void shouldExecuteInsert() {
        given(genreService.findOrCreateByName(EXISTING_GENRE_NAME)).willReturn(EXISTING_GENRE_ID);
        given(authorService.findOrCreateByFio(EXISTING_AUTHOR_FIO)).willReturn(EXISTING_AUTHOR_ID);

        Book book = new Book(EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXPECTING_BOOK_TITLE);
        given(bookDao.getByParams(book)).willReturn(List.of());
        given(bookDao.insert(book)).willReturn((long) 2);

        var id = bookService.insert(EXPECTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);

        assertThat(id).isEqualTo(2);

        Mockito.verify(bookDao, Mockito.times(1)).insert(book);
    }

    @Test
    @DisplayName("падать с ошибкой при не переданных параметрах книги")
    void shouldFailWhenEmptyTitle() {
        Assertions.assertThrows(BoolServiceException.class, () -> {
            bookService.insert("", EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BoolServiceException.class, () -> {
            bookService.insert(EXISTING_BOOK_TITLE, "", EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BoolServiceException.class, () -> {
            bookService.insert(EXISTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, "");
        });
        Assertions.assertThrows(BoolServiceException.class, () -> {
            bookService.update(EXISTING_BOOK_ID,"", EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BoolServiceException.class, () -> {
            bookService.update(EXISTING_BOOK_ID,EXISTING_BOOK_TITLE, "", EXISTING_GENRE_NAME);
        });
        Assertions.assertThrows(BoolServiceException.class, () -> {
            bookService.update(EXISTING_BOOK_ID,EXISTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, "");
        });
    }

    @Test
    @DisplayName("возвращать id существующей книги")
    void insert() {
        given(genreService.findOrCreateByName(EXISTING_GENRE_NAME)).willReturn(EXISTING_GENRE_ID);
        given(authorService.findOrCreateByFio(EXISTING_AUTHOR_FIO)).willReturn(EXISTING_AUTHOR_ID);

        Book book1 = new Book(EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_TITLE);
        Book book2 = new Book(EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_TITLE);
        book2.setId(EXISTING_BOOK_ID);
        given(bookDao.getByParams(book1)).willReturn(List.of(book2));

        var id = bookService.insert(EXISTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);
        assertThat(id).isEqualTo(EXISTING_BOOK_ID);

        Mockito.verify(bookDao, Mockito.times(0)).insert(any());
    }

    @Test
    @DisplayName("изменять параметры книги")
    void shouldUpdateBook() {
        Book book = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_TITLE);
        given(bookDao.getById(EXISTING_BOOK_ID)).willReturn(book);

        given(genreService.findOrCreateByName(EXPECTING_GENRE_NAME)).willReturn(EXPECTING_GENRE_ID);
        given(authorService.findOrCreateByFio(EXPECTING_AUTHOR_FIO)).willReturn(EXPECTING_AUTHOR_ID);

        bookService.update(EXISTING_BOOK_ID, EXPECTING_BOOK_TITLE, EXPECTING_AUTHOR_FIO, EXPECTING_GENRE_NAME);

        Book book2 = new Book(EXISTING_BOOK_ID, EXPECTING_AUTHOR_ID, EXPECTING_GENRE_ID, EXPECTING_BOOK_TITLE);

        Mockito.verify(bookDao).update(book2);
    }
}