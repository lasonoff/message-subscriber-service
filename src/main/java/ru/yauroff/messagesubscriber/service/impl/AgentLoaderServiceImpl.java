package ru.yauroff.messagesubscriber.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yauroff.messagesubscriber.repository.AgentRepository;
import ru.yauroff.messagesubscriber.service.AgentLoaderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    public void loadAll() throws IOException {
        log.info("Agent load url: {}", loadUrl);
        URL url = new URL(loadUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        log.info("STATUS {}", status);
        log.info(content.toString());
    }
}
