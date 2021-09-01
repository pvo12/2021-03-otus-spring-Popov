package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.RestTemplatePropertiesConfig;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookMessage;
import ru.otus.spring.messaging.MessageProducer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final RestService service;
    private final RestTemplatePropertiesConfig config;
    private final MessageProducer messageProducer;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private CircuitBreaker circuitBreaker;

    @PostConstruct
    public void init() {
        circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
    }

    @Override
    public List<Book> getAll() {
        return Arrays.asList(service.getForEntity(config.getGetBookUrl(), Book[].class).getBody());
    }

    @Override
    public Book getById(long id) {
        return circuitBreaker.run(() -> service.getForEntity(config.getGetBookUrl() + id, Book.class).getBody(),
            throwable -> new Book(id,"N/A", "N/A", "N/A"));
    }

    @Override
    public void add(String bookTitle, String authorFio, String genreName) {
        messageProducer.send(new BookMessage(bookTitle, authorFio, genreName));
    }
}
