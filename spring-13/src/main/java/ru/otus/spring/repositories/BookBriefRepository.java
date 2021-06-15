package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.BookBrief;

public interface BookBriefRepository extends JpaRepository<BookBrief, Long> {
}
