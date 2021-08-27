package ru.otus.spring.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.BookMessage;
import ru.otus.spring.service.BookService;

@Service
@RequiredArgsConstructor
public class KafkaMessageConsumer implements MessageConsumer {
    private final BookService service;

    @KafkaListener(
            topics = "${library.kafka-topic-add-book-name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void listen(BookMessage book) {
        service.insert(book.getTitle(), book.getTitle(), book.getGenre());
    }
}
