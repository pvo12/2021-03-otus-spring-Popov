package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.RestTemplateConfig;
import ru.otus.spring.domain.Book;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final RestService service;
    private final RestTemplateConfig config;

    @Cacheable("books")
    @Override
    public List<Book> getAll() {
        return Arrays.asList(service.getAuthorizedTemplate().getForEntity(config.getGetBookUrl(), Book[].class).getBody());
    }
}
