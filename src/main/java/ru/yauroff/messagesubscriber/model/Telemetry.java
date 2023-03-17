package ru.yauroff.messagesubscriber.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Telemetry implements Persistable<String> {
    @Id
    private String uuid;
    private String agent_id;
    private Long previous_message_time;
    private String active_service;
    private Integer quality_score;

    @Transient
    private boolean newProduct;

    @Override
    public String getId() {
        return uuid;
    }

    @Override
    public boolean isNew() {
        return this.isNewProduct();
    }
}
