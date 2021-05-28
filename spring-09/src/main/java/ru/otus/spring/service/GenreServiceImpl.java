package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public Genre findOrCreateByName(String name) {
        var genres = genreDao.getByName(name);
        if (genres.size() == 0) {
            Genre genre = new Genre(0, name);
            genre.setId(genreDao.insert(genre));
            return genre;
        } else {
            return genres.get(0);
        }
    }
}
