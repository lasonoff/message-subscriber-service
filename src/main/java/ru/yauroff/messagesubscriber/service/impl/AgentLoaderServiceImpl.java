package ru.yauroff.messagesubscriber.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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
    public Mono<Void> loadAll() {
        log.info("Agent load url: {}", loadUrl);
        WebClient webClient = WebClient.create();


        String res = webClient.get()
                              .uri(loadUrl)
                              .retrieve()
                              .bodyToMono(String.class)
                              .block();
        log.info("Responce: {}", res);
        return null;
    }
}
