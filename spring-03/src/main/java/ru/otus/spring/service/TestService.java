package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionLoadingException;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

public interface TestService {

    void test() throws QuestionLoadingException;
}
