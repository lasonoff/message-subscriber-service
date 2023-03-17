package ru.yauroff.messagesubscriber.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.yauroff.messagesubscriber.dto.TelemetryDTO;
import ru.yauroff.messagesubscriber.model.Telemetry;
import ru.yauroff.messagesubscriber.service.TelemetryReceiverService;
import ru.yauroff.messagesubscriber.service.TelemetryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelemetryReceiverServiceImpl implements TelemetryReceiverService {
    private final ObjectMapper objectMapper;
    private final TelemetryService telemetryService;

    @Override
    @Async
    @KafkaListener(topics = "${kafka.topic}")
    public void listener(ConsumerRecord<String, String> record) {
        log.debug("Record key: {}, value: {} ", record.key(), record.value());
        TelemetryDTO telemetryDTO;
        try {
            telemetryDTO = objectMapper.readValue(record.value(), TelemetryDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Parsing record {}", record.value());
            return;
        }
        Telemetry telemetry = telemetryDTO.toTelemetry();
        telemetryService.save(telemetry)
                        .subscribe(null, error -> log.error("Error {}", error.getMessage()));
    }
}
