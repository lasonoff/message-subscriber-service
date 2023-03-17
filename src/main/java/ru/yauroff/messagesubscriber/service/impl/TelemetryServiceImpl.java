package ru.yauroff.messagesubscriber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.model.Telemetry;
import ru.yauroff.messagesubscriber.repository.TelemetryRepository;
import ru.yauroff.messagesubscriber.service.TelemetryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelemetryServiceImpl implements TelemetryService {
    private final TelemetryRepository telemetryRepository;

    @Override
    public Mono<Telemetry> save(Telemetry telemetry) {
        return telemetryRepository.save(telemetry);
    }
}
