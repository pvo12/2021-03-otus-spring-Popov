package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public Author findOrCreateByFio(String fio) {
        var authors = authorDao.getByFio(fio);
        if (authors.size() == 0) {
            Author author = new Author(0, fio);
            author.setId(authorDao.insert(author));
            return author;
        } else {
            return authors.get(0);
        }
    }
}
