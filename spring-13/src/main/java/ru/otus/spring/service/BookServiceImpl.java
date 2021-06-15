package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repositories.BookCommentRepository;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookCommentRepository bookCommentRepository;

    @Override
    @Transactional
    public void delete(long id) {
        bookCommentRepository.deleteByBookId(id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return repository.findById(id).orElseThrow(() -> new BookServiceException("Book not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public long insert(String bookTitle, String authorFio, String genreName) {
        checkBookParams(bookTitle, authorFio, genreName);

        var genre = genreService.findOrCreateByName(genreName);
        var author = authorService.findOrCreateByFio(authorFio);

        var book = new Book(author, genre, bookTitle);
        var books = repository.findAll(Example.of(book));
        if (books.size() == 0) {
            return repository.save(book).getId();
        } else {
            return books.get(0).getId();
        }
    }

    private void checkBookParams(String bookTitle, String authorFio, String genreName) {
        if (!hasLength(bookTitle)) {
            throw new BookServiceException("Title can't be blank");
        }
        if (!hasLength(authorFio)) {
            throw new BookServiceException("Author can't be blank");
        }
        if (!hasLength(genreName)) {
            throw new BookServiceException("Genre can't be blank");
        }
    }

    @Override
    @Transactional
    public void update(long id, String bookTitle, String authorFio, String genreName) {
        checkBookParams(bookTitle, authorFio, genreName);

        var book = repository.findById(id).orElseThrow(() -> new BookServiceException("Book not found"));

        book.setTitle(bookTitle);
        book.setAuthor(authorService.findOrCreateByFio(authorFio));
        book.setGenre(genreService.findOrCreateByName(genreName));

        repository.save(book);
    }
}
