package ru.otus.spring.service;

import org.springframework.web.client.RestTemplate;

public interface RestService {
    RestTemplate getAuthorizedTemplate();
}
