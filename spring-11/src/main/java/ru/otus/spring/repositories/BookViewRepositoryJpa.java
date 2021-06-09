package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class BookViewRepositoryJpa implements BookViewRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<BookView> findById(long id) {
        return Optional.ofNullable(em.find(BookView.class, id));
    }
}
