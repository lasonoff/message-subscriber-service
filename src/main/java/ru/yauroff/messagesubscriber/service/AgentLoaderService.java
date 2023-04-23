package ru.yauroff.messagesubscriber.service;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.MalformedURLException;

public interface AgentLoaderService {

    void loadAll() throws IOException;
}
