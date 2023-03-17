package ru.yauroff.messagesubscriber.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.yauroff.messagesubscriber.model.Agent;

public interface AgentRepository extends ReactiveCrudRepository<Agent, String> {
}
