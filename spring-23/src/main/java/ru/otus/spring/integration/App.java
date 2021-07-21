package ru.otus.spring.integration;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;

import java.util.concurrent.ForkJoinPool;


@IntegrationComponentScan
@SuppressWarnings({ "resource", "Duplicates", "InfiniteLoopStatement" })
@ComponentScan
@Configuration
@EnableIntegration
public class App {
    private static final String[] TYPES = { "Cabbage White", "Swallowtail", "Brush-footed", "American Snout"};

    @Bean
    public QueueChannel itemsChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }

    @Bean
    public IntegrationFlow woodFlow() {
        return IntegrationFlows.from( "caterpillarChannel" )
                .handle( "metamorphosisService", "turn" )
                .channel( "butterflyChannel" )
                .get();
    }

    public static void main( String[] args ) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext( App.class );

        // here we works with cafe using interface
        Cocoon cocoon = ctx.getBean( Cocoon.class );

        ForkJoinPool pool = ForkJoinPool.commonPool();

        while ( true ) {
            Thread.sleep( 7000 );

            pool.execute( () -> {
                Caterpillar caterpillar = generateOrderItem();
                System.out.println( "New caterpillar: " + caterpillar.getName());
                Butterfly butterfly = cocoon.process( caterpillar );
                System.out.println( "Turned butterfly: " + butterfly.getName());
            } );
        }
    }

    private static Caterpillar generateOrderItem() {
        return new Caterpillar( TYPES[ RandomUtils.nextInt( 0, TYPES.length ) ] );
    }
}
