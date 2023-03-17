package ru.yauroff.messagesubscriber.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.model.Telemetry;
import ru.yauroff.messagesubscriber.repository.TelemetryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelemetryServiceImplTest {
    private TelemetryServiceImpl instance;

    @Mock
    private TelemetryRepository telemetryRepositoryMock;

    @BeforeEach
    public void before() {
        this.instance = new TelemetryServiceImpl(this.telemetryRepositoryMock);
    }

    @Test
    void save() {
        Telemetry telemetry = new Telemetry();
        Mono<Telemetry> returnMono = new Mono<>() {
            @Override
            public void subscribe(CoreSubscriber<? super Telemetry> coreSubscriber) {

            }
        };
        when(this.telemetryRepositoryMock.save(telemetry))
                .thenReturn(returnMono);

        assertEquals(this.instance.save(telemetry), returnMono);
    }
}