package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface TestService {

    List<Question> getAll();

    int getLimit();
}
