package ru.yauroff.messagesubscriber.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.yauroff.messagesubscriber.dto.AgentDTO;
import ru.yauroff.messagesubscriber.repository.AgentRepository;
import ru.yauroff.messagesubscriber.service.AgentLoaderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class AgentLoaderServiceImpl implements AgentLoaderService {

    private final AgentRepository agentRepository;

    private final ObjectMapper objectMapper;

    @Value("${data.agent.load.url}")
    private String loadUrl;

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void loadAll() {
        CompletableFuture.runAsync(() -> {
            try {
                loadAllRun();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });
    }

    private void loadAllRun() throws IOException {
        String requestData = loadFromUrl();
        List<AgentDTO> agents = objectMapper.readValue(requestData, new TypeReference<List<AgentDTO>>() {
        });
        Flux.fromIterable(agents)
            .map(agentDto -> agentDto.toAgent())
            .flatMap(agent -> agentRepository.save(agent))
            .subscribe(value -> log.info("Add agent {}", value),
                    error -> log.error("Error {}", error.getMessage()));
    }

    private String loadFromUrl() throws IOException {
        log.info("Agent load url: {}", loadUrl);
        URL url = new URL(loadUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }
}
