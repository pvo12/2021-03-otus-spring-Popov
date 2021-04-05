package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
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
    public Boolean test() throws IOException {
        Boolean result = false;
        inOutService.println("Enter your firstname and lastname");
        inOutService.nextLine();
        int countAnswer = 0;

        List<Question> questions = dao.findAll();
        for (Question question : questions) {
            inOutService.println(question.getQuestion());
            if (!question.getAnswers().isEmpty()) {
                inOutService.println("Answer options:");
                for (String answer : question.getAnswers()) {
                    inOutService.println("  " + answer);
                }
            }
            String answer = inOutService.nextLine();
            if (answer.equals(question.getAnswer())) {
                countAnswer++;
            }
            inOutService.println();
        }

        if (countAnswer >= limit) {
            inOutService.println("Test passed");
            result = true;
        } else {
            inOutService.println("Test failed");
        }
        return result;
    }
}
