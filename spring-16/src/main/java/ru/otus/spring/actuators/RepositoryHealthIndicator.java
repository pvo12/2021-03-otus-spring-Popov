package ru.otus.spring.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.service.BookService;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RepositoryHealthIndicator implements HealthIndicator {
    private final BookRepository repository;

    @Override
    public Health health() {
        boolean serverIsDown = true;
        try {
            serverIsDown = repository.count() == 0;
        } catch (Exception e) {
        }
        if (serverIsDown) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Караул!")
                    .build();
        } else {
            return Health.up().withDetail("message", "Ура, товарищи!").build();
        }
    }
}
