package ru.yauroff.messagesubscriber.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.dto.TelemetryDTO;
import ru.yauroff.messagesubscriber.model.Telemetry;
import ru.yauroff.messagesubscriber.service.TelemetryService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelemetryReceiverServiceImplTest {
    @Mock
    private ObjectMapper objectMapperMock;

    @Mock
    private TelemetryService telemetryServiceMock;

    private TelemetryReceiverServiceImpl instance;

    @BeforeEach
    public void before() {
        this.instance = new TelemetryReceiverServiceImpl(this.objectMapperMock, this.telemetryServiceMock);
    }

    @Test
    void listener_correct() throws JsonProcessingException {
        ConsumerRecord<String, String> record = new ConsumerRecord<>("topic", 1, 1L, "key", "value");
        TelemetryDTO telemetryDTO = new TelemetryDTO("1", "1", 1L, "1", 1);
        Telemetry telemetry = new Telemetry("1", "1", 1L, "1", 1, true);
        when(objectMapperMock.readValue(record.value(), TelemetryDTO.class)).thenReturn(telemetryDTO);
        when(telemetryServiceMock.save(telemetry)).thenReturn(new Mono<>() {
            @Override
            public void subscribe(CoreSubscriber<? super Telemetry> coreSubscriber) {
            }
        });

        this.instance.listener(record);
        ArgumentCaptor<String> argumentRecordValue1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Class> argumentDTOClass1 = ArgumentCaptor.forClass(Class.class);
        verify(this.objectMapperMock).readValue(argumentRecordValue1.capture(), argumentDTOClass1.capture());

        ArgumentCaptor<Telemetry> argumentTelemetry = ArgumentCaptor.forClass(Telemetry.class);
        verify(this.telemetryServiceMock).save(argumentTelemetry.capture());
    }

    @Test
    void listener_fail() throws JsonProcessingException {
        ConsumerRecord<String, String> record = new ConsumerRecord<>("topic", 1, 1L, "key", "value");
        TelemetryDTO telemetryDTO = new TelemetryDTO("1", "1", 1L, "1", 1);
        Telemetry telemetry = new Telemetry("1", "1", 1L, "1", 1, true);
        when(objectMapperMock.readValue(record.value(), TelemetryDTO.class)).thenReturn(telemetryDTO);

        // Error parsing exception
        when(objectMapperMock.readValue(record.value(), TelemetryDTO.class)).thenThrow(JsonProcessingException.class);
        ArgumentCaptor<String> argumentRecordValue = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Class> argumentDTOClass = ArgumentCaptor.forClass(Class.class);

        this.instance.listener(record);
        verify(this.objectMapperMock).readValue(argumentRecordValue.capture(), argumentDTOClass.capture());
        ArgumentCaptor<Telemetry> argumentTelemetry = ArgumentCaptor.forClass(Telemetry.class);
        verify(this.telemetryServiceMock, never()).save(argumentTelemetry.capture());
    }
}