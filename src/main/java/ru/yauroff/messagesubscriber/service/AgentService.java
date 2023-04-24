package ru.yauroff.messagesubscriber.service;

import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.model.Agent;

public interface AgentService {

    Mono<Agent> findById(String id);

    Mono<Agent> save(Agent agent);
}
