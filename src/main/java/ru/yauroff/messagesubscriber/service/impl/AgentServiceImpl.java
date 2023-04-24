package ru.yauroff.messagesubscriber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.model.Agent;
import ru.yauroff.messagesubscriber.repository.AgentRepository;
import ru.yauroff.messagesubscriber.service.AgentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;

    @Override
    public Mono<Agent> findById(String id) {
        return agentRepository.findById(id);
    }

    @Override
    public Mono<Agent> save(Agent agent) {
        return agentRepository.save(agent);
    }
}
