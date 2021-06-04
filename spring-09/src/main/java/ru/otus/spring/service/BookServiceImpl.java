package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Override
    public void delete(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public long insert(String bookTitle, String authorFio, String genreName) {
        checkBookParams(bookTitle, authorFio, genreName);

        var genre = genreService.findOrCreateByName(genreName);
        var author = authorService.findOrCreateByFio(authorFio);

        var book = new Book(author, genre, bookTitle);
        var books = bookDao.getByExample(book);
        if (books.size() == 0) {
            return bookDao.insert(book);
        } else {
            return books.get(0).getId();
        }
    }

    private void checkBookParams(String bookTitle, String authorFio, String genreName) {
        if (StringUtils.isNullOrEmpty(bookTitle)) {
            throw new BookServiceException("Title can't be blank");
        }
        if (StringUtils.isNullOrEmpty(authorFio)) {
            throw new BookServiceException("Author can't be blank");
        }
        if (StringUtils.isNullOrEmpty(genreName)) {
            throw new BookServiceException("Genre can't be blank");
        }
    }

    @Override
    public void update(long id, String bookTitle, String authorFio, String genreName) {
        checkBookParams(bookTitle, authorFio, genreName);

        var book = bookDao.getById(id);

        book.setTitle(bookTitle);
        book.setAuthor(authorService.findOrCreateByFio(authorFio));
        book.setGenre(genreService.findOrCreateByName(genreName));

        bookDao.update(book);
    }
}
