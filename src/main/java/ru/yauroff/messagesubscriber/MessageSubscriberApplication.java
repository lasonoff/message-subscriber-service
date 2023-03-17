package ru.yauroff.messagesubscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MessageSubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageSubscriberApplication.class, args);
    }

}
