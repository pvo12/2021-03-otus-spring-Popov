package ru.otus.spring.service;

public interface AuthorService {
    long findOrCreateByFio(String fio);
}
