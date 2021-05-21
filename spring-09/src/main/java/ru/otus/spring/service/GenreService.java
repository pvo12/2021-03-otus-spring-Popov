package ru.otus.spring.service;

public interface GenreService {
    long findOrCreateByName(String name);
}
