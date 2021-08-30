package ru.otus.spring.messaging;

import ru.otus.spring.domain.BookMessage;

public interface MessageProducer {
    void send(BookMessage book);
}
