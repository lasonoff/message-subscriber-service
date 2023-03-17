package ru.yauroff.messagesubscriber.service;

import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.model.Telemetry;

public interface TelemetryService {

    Mono<Telemetry> save(Telemetry telemetry);
}
