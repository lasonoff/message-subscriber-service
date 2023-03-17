package ru.yauroff.messagesubscriber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yauroff.messagesubscriber.model.Agent;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgentDTO {
    private String agent_id;
    private String manufactured;
    private String os;

    public Agent toAgent() {
        Agent agent = new Agent(this.agent_id, this.manufactured, this.os, true);
        return agent;
    }
}
