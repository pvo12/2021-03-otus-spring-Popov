package ru.otus.spring.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;

@MessagingGateway
public interface Cocoon {

    @Gateway(requestChannel = "caterpillarChannel", replyChannel = "butterflyChannel")
    Butterfly process(Caterpillar caterpillar);
}
