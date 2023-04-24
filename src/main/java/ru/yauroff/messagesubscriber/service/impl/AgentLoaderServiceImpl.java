package ru.yauroff.messagesubscriber.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yauroff.messagesubscriber.dto.AgentDTO;
import ru.yauroff.messagesubscriber.model.Agent;
import ru.yauroff.messagesubscriber.repository.AgentRepository;
import ru.yauroff.messagesubscriber.service.AgentLoaderService;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class AgentLoaderServiceImpl implements AgentLoaderService {

    private final WebClient webClient;

    private final AgentRepository agentRepository;

    @Override
    public Mono<Agent> loadById(String id) {
        log.info("Agent load: {}", String.join("", "/", id));
        return webClient
                .get()
                .uri(String.join("", "/", id))
                .retrieve()
                .bodyToMono(AgentDTO.class)
                .map(agentDto -> agentDto.toAgent());
    }
}
