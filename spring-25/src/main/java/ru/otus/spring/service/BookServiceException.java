package ru.otus.spring.service;

public class BookServiceException extends RuntimeException {
    public BookServiceException(String message) {
        super(message);
    }
}
