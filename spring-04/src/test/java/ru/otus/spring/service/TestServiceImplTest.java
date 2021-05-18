package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.Config;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionLoadingException;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.InOutService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.TestServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @Mock
    private QuestionDao dao;
    @Mock
    private InOutService inOutService;
    private List<String> answers = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private Config config;
    @Mock
    private LocalizationService localizationService;
    private static final String TEST_PASSED = ": Test passed";
    private static final String TEST_FAILED = ": Test failed";

    @BeforeEach
    public void setUp() throws QuestionLoadingException {
        config = new Config();
        config.setLimit(1);
        config.setLocale(Locale.forLanguageTag("en-US"));

        questions.add(new Question("2+2", answers, "4"));
        questions.add(new Question("2+1", answers, "3"));
        Mockito.when(dao.findAll()).thenReturn(questions);

        Mockito.when(localizationService.getMessage(eq("enter.name"))).thenReturn("Enter your firstname and lastname");
    }

    @DisplayName("должен запрашивать имя")
    @Test
    public void shouldRequestName() throws QuestionLoadingException {
        TestServiceImpl service = new TestServiceImpl(dao, inOutService, config, localizationService);
        service.test();
        Mockito.verify(inOutService).println("Enter your firstname and lastname");
    }

    @DisplayName("должен проходить тест с количеством правильных ответов = лимиту")
    @Test
    public void shouldCompleteTestWithLimitAnswers() throws QuestionLoadingException {
        Mockito.when(localizationService.getMessage(eq("test.passed"))).thenReturn("Test passed");
        TestServiceImpl service = new TestServiceImpl(dao, inOutService, config, localizationService);
        Mockito.when(inOutService.nextLine()).thenReturn("name").thenReturn("4");
        service.test();
        Mockito.verify(inOutService).println("name" + TEST_PASSED);
    }

    @DisplayName("должен проходить тест с количеством правильных ответов больше лимита")
    @Test
    public void shouldCompleteTestHigherLimitAnswers() throws QuestionLoadingException {
        Mockito.when(localizationService.getMessage(eq("test.passed"))).thenReturn("Test passed");
        config.setLimit(0);
        TestServiceImpl service = new TestServiceImpl(dao, inOutService, config, localizationService);
        Mockito.when(inOutService.nextLine()).thenReturn("name").thenReturn("4").thenReturn("3");
        service.test();
        Mockito.verify(inOutService).println("name" + TEST_PASSED);
    }

    @DisplayName("должен не проходить тест с количеством правильных ответов меньше лимита")
    @Test
    public void shouldFailedTestWithLowerLimitAnswers() throws QuestionLoadingException {
        Mockito.when(localizationService.getMessage(eq("test.failed"))).thenReturn("Test failed");
        config.setLimit(2);
        TestServiceImpl service = new TestServiceImpl(dao, inOutService, config, localizationService);
        Mockito.when(inOutService.nextLine()).thenReturn("name").thenReturn("4");
        service.test();
        Mockito.verify(inOutService).println("name" + TEST_FAILED);
    }
}