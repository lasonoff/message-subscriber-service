package ru.yauroff.messagesubscriber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yauroff.messagesubscriber.model.Telemetry;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelemetryDTO {
    private String uuid;
    private String agent_id;
    private Long previous_message_time;
    private String active_service;
    private Integer quality_score;

    public Telemetry toTelemetry() {
        Telemetry telemetry = new Telemetry(uuid, agent_id, previous_message_time, active_service, quality_score, true);
        return telemetry;
    }
}