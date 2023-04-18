package de.crfa.app.crfametadataservicereloaded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OffChainDataCrawler {

    @Autowired
    private HttpClient httpClient;

    // crawls url and returns json of offchain metadata part
    public String crawl(String url) throws IOException, InterruptedException {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

}
