package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.RestTemplatePropertiesConfig;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookMessage;
import ru.otus.spring.messaging.MessageProducer;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final RestService service;
    private final RestTemplatePropertiesConfig config;
    private final MessageProducer messageProducer;

    @Override
    public List<Book> getAll() {
        return Arrays.asList(service.getForEntity(config.getGetBookUrl(), Book[].class).getBody());
    }

    @Override
    public Book getById(long id) {
        return service.getForEntity(config.getGetBookUrl() + id, Book.class).getBody();
    }

    @Override
    public void add(String bookTitle, String authorFio, String genreName) {
        messageProducer.send(new BookMessage(bookTitle, authorFio, genreName));
    }
}
