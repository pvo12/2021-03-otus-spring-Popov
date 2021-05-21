package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.Config;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionLoadingException;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionDao dao;
    private final InOutService inOutService;
    private final Config config;
    private final LocalizationService localizationService;

    @Override
    public void test() throws QuestionLoadingException {
        String userName = getUserName();
        int countAnswer = 0;

        List<Question> questions = dao.findAll();
        for (Question question : questions) {
            String answer = getAnswer(question);
            if (answer != null && answer.equals(question.getAnswer())) {
                countAnswer++;
            }
            inOutService.println();
        }
        printResult(userName, countAnswer);
    }

    private void printResult(String userName, int countAnswer) {
        if (countAnswer >= config.getLimit()) {
            inOutService.println(userName + ": " + localizationService.getMessage("test.passed"));
        } else {
            inOutService.println(userName + ": " + localizationService.getMessage("test.failed"));
        }
    }

    private String getAnswer(Question question) {
        inOutService.println(question.getQuestion());
        if (!question.getAnswers().isEmpty()) {
            inOutService.println(localizationService.getMessage("answer.options"));
            for (String answer : question.getAnswers()) {
                inOutService.println("  " + answer);
            }
        }
        return inOutService.nextLine();
    }

    private String getUserName() {
        inOutService.println(localizationService.getMessage("enter.name"));
        return inOutService.nextLine();
    }


}
