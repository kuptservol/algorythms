package ru.skuptsov.algorythms.paxos;

import com.google.common.eventbus.EventBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Сергей on 01.03.2016.
 */
@Configuration
@ComponentScan("ru.skuptsov.paxos")
public class Paxos {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Paxos.class);
    }

    @Bean
    public EventBus eventBus(){
        return new EventBus();
    }
}
