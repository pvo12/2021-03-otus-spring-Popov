package ru.otus.spring.service;

import org.junit.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;

import static org.junit.Assert.assertTrue;

public class QuestionServiceImplTest {
    @Test
    public void getAll() {
        QuestionDao dao = Mockito.mock(QuestionDao.class);
        QuestionServiceImpl service = new QuestionServiceImpl(dao);
        assertTrue(service.getAll().isEmpty());
    }
}