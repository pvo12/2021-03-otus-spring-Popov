package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(" select s " +
            "  from Book s " +
            "  join fetch s.author " +
            "  join fetch s.genre " +
            " where s.title  = :#{#book.title} " +
            "   and s.author = :#{#book.author} " +
            "   and s.genre  = :#{#book.genre}")
    List<Book> findByExample(@Param("book") Book book);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
