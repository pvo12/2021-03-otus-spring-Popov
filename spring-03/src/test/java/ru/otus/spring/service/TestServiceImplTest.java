package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestServiceImplTest {
    @Test
    public void test() throws IOException {
        QuestionDao dao = Mockito.mock(QuestionDao.class);

        List<String> answers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("2+2", answers, "4"));
        Mockito.when(dao.findAll()).thenReturn(questions);

        InOutService inOutService = Mockito.mock(InOutService.class);
        Mockito.when(inOutService.nextLine()).thenReturn("name").thenReturn("3");

        TestServiceImpl service = new TestServiceImpl(dao, 1, inOutService);
        assertFalse(service.test());

        Mockito.when(inOutService.nextLine()).thenReturn("name").thenReturn("4");
        assertTrue(service.test());
    }
}