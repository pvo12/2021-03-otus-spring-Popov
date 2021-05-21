package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.dao.QuestionLoadingException;
import ru.otus.spring.service.TestService;

@SpringBootApplication
public class Main {
	public static void main(String[] args) throws QuestionLoadingException {
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		TestService service = context.getBean(TestService.class);
		service.test();
	}
}
