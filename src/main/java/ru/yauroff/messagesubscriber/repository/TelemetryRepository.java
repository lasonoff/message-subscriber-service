package ru.yauroff.messagesubscriber.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.yauroff.messagesubscriber.model.Telemetry;

public interface TelemetryRepository extends ReactiveCrudRepository<Telemetry, String> {
}
