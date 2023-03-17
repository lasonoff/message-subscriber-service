package ru.yauroff.messagesubscriber.service;

import reactor.core.publisher.Mono;

public interface AgentLoaderService {

    Mono<Void> loadAll();
}
