package ru.otus.spring.messaging;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.BookMessage;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer implements MessageProducer {
    private final KafkaTemplate<String, BookMessage> kafkaTemplate;

    @Value(value = "${library.kafka-topic-add-book-name}")
    private String topicName;

    @SneakyThrows
    @Override
    public void send(BookMessage book) {
        kafkaTemplate.send(topicName, book);
    }
}
