package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookComment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookCommentRepositoryJpa implements BookCommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() <= 0) {
            em.persist(bookComment);
            return bookComment;
        } else {
            return em.merge(bookComment);
        }
    }

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> findAll() {
        TypedQuery<BookComment> query = em.createQuery("select s from BookComment s " +
                "join fetch s.book", BookComment.class);

        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from BookComment s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteByBookId(long id) {
        Query query = em.createQuery("delete " +
                "from BookComment s " +
                "where s.book_id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
