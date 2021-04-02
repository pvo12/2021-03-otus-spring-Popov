package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao dao;

    @Value("${test.limit}")
    private int limit;

    public TestServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public List<Question> getAll() {
        return dao.findAll();
    }

    public int getLimit() {
        return limit;
    }
}
