package ru.yauroff.messagesubscriber.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yauroff.messagesubscriber.dto.AgentDTO;
import ru.yauroff.messagesubscriber.repository.AgentRepository;
import ru.yauroff.messagesubscriber.service.AgentLoaderService;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class AgentLoaderServiceImpl implements AgentLoaderService {

    private final AgentRepository agentRepository;
    //private final WebClient webClient;

    @Value("${data.agent.load.url}")
    private String loadUrl;

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void loadAll() {
        log.info("Agent load url: {}", loadUrl);
        WebClient webClient = WebClient.create();

        webClient.get()
                 .uri(loadUrl)
                 .header("Content-Type", "application/json")
                 .retrieve()
                 .bodyToFlux(AgentDTO.class)
                 .map(agentDto -> agentDto.toAgent())
                 .flatMap(agent -> agentRepository.save(agent))
                 .subscribe(value -> log.info("Add agent {}", value),
                         error -> log.error("Error {}", error.getMessage()));
    }
}
