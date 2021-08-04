package ru.otus.spring.service;

public interface TokenService {
    String getToken(String username, String password);
}
