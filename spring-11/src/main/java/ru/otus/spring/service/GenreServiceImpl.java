package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    @Override
    @Transactional
    public Genre findOrCreateByName(String name) {
        var genres = repository.findByName(name);
        if (genres.size() == 0) {
            Genre genre = new Genre(0, name);
            return repository.save(genre);
        } else {
            return genres.get(0);
        }
    }
}
