package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.TestService;

import java.util.List;
import java.util.Scanner;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        Scanner in = new Scanner(System.in);

        System.out.println("Enter your firstname and lastname");
        in.nextLine();

        TestService service = context.getBean(TestService.class);

        int countAnswer = 0;

        List<Question> questions = service.getAll();
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            if (!question.getAnswers().isEmpty()) {
                System.out.println("Answer options:");
                for (String answer : question.getAnswers()) {
                    System.out.println("  " + answer);
                }
            }
            String answer = in.nextLine();
            if (answer.equals(question.getAnswer())) {
                countAnswer++;
            }
            System.out.println();
        }

        if (countAnswer >= service.getLimit()) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }

        context.close();
    }
}
