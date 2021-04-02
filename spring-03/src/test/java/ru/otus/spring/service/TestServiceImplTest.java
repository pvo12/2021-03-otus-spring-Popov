package ru.otus.spring.service;

import org.junit.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;

import static org.junit.Assert.assertTrue;

public class TestServiceImplTest {
    @Test
    public void getAll() {
        QuestionDao dao = Mockito.mock(QuestionDao.class);
        TestServiceImpl service = new TestServiceImpl(dao);
        assertTrue(service.getAll().isEmpty());
    }
}