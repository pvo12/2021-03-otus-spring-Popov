package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookBrief;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class BookBriefRepositoryJpa implements BookBriefRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<BookBrief> findById(long id) {
        return Optional.ofNullable(em.find(BookBrief.class, id));
    }
}
