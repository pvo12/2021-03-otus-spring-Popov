package ru.otus.spring.integration.kitchen;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;

@Service
public class MetamorphosisService {

    public Butterfly turn(Caterpillar caterpillar) throws Exception {
        System.out.println("Turn " + caterpillar.getName());
        Thread.sleep(3000);
        System.out.println("Turn " + caterpillar.getName() + " done");
        return new Butterfly(caterpillar.getName());
    }
}
