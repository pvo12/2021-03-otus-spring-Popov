package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        List<Question> questions = service.getAll();
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            if (!question.getAnswers().isEmpty()) {
                System.out.println("Answer options:");
                for (String answer : question.getAnswers()) {
                    System.out.println("  " + answer);
                }
            }
            System.out.println();
        }

        // Данная операция, в принципе не нужна.
        // Мы не работаем пока что с БД, а Spring Boot сделает закрытие за нас
        // Подробности - через пару занятий
        context.close();
    }
}
