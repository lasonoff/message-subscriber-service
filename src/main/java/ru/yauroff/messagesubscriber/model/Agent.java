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
public class Agent implements Persistable<String> {
    @Id
    private String agent_id;
    private String manufactured;
    private String os;

    @Transient
    private boolean newProduct;

    @Override
    public String getId() {
        return agent_id;
    }

    @Override
    public boolean isNew() {
        return this.isNewProduct();
    }
}
