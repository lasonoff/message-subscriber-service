package ru.yauroff.messagesubscriber.service;

import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.model.Agent;

public interface AgentLoaderService {

    Mono<Agent> loadById(String id);
}
