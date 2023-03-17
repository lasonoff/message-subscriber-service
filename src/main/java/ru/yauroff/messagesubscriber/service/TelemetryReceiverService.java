package ru.yauroff.messagesubscriber.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface TelemetryReceiverService {

    void listener(ConsumerRecord<String, String> record);

}
