package ru.otus.spring.service;

import org.springframework.http.ResponseEntity;

public interface RestService {
    <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType);
}
