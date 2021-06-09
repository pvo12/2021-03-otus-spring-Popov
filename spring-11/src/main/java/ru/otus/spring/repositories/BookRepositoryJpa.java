package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select s from Book s " +
                "join fetch s.author " +
                "join fetch s.genre ", Book.class).getResultList();
    }

    @Override
    public List<Book> findByExample(Book book) {
        TypedQuery<Book> query = em.createQuery("select s " +
                        "from Book s " +
                        "join fetch s.author " +
                        "join fetch s.genre " +
                        "where s.title = :title " +
                        "and s.author = :author " +
                        "and s.genre = :genre",
                Book.class);
        query.setParameter("title", book.getTitle());
        query.setParameter("author", book.getAuthor());
        query.setParameter("genre", book.getGenre());
        return query.getResultList();
    }
}
