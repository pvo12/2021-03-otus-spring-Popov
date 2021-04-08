package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionLoadingException;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao dao;
    private final int limit;
    private final InOutService inOutService;

    public TestServiceImpl(QuestionDao dao, @Value("${test.limit}") int limit, InOutService inOutService) {
        this.dao = dao;
        this.limit = limit;
        this.inOutService = inOutService;
    }

    @Override
    public void test() throws QuestionLoadingException {
        String userName = getUserName();
        int countAnswer = 0;

        List<Question> questions = dao.findAll();
        for (Question question : questions) {
            String answer = getAnswer(question);
            if (answer.equals(question.getAnswer())) {
                countAnswer++;
            }
            inOutService.println();
        }
        printResult(userName, countAnswer);
    }

    private void printResult(String userName, int countAnswer) {
        if (countAnswer >= limit) {
            inOutService.println(userName + ": Test passed");
        } else {
            inOutService.println(userName + ": Test failed");
        }
    }

    private String getAnswer(Question question) {
        inOutService.println(question.getQuestion());
        if (!question.getAnswers().isEmpty()) {
            inOutService.println("Answer options:");
            for (String answer : question.getAnswers()) {
                inOutService.println("  " + answer);
            }
        }
        return inOutService.nextLine();
    }

    private String getUserName() {
        inOutService.println("Enter your firstname and lastname");
        return inOutService.nextLine();
    }


}
