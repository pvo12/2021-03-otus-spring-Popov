package ru.otus.spring.messaging;

import ru.otus.spring.domain.BookMessage;

public interface MessageConsumer {
    void listen(BookMessage book);
}
