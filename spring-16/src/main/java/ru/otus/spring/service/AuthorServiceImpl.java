package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repositories.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    @Override
    @Transactional
    public Author findOrCreateByFio(String fio) {
        var authors = repository.findByFio(fio);
        if (authors.size() == 0) {
            Author author = new Author(0, fio);
            return repository.save(author);
        } else {
            return authors.get(0);
        }
    }
}
