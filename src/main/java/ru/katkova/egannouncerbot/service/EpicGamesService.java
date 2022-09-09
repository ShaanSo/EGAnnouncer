package ru.katkova.egannouncerbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.katkova.egannouncerbot.ApplicationProperties;

@Slf4j
@Service
@AllArgsConstructor
public class EpicGamesService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private final ApplicationProperties properties;

    public String handleRequest()
            throws JsonProcessingException, RestClientException {

        log.info("[EGService] Request received. Headers: ");
        HttpEntity<String> entityRq = new HttpEntity<>("", new org.springframework.http.HttpHeaders());

        var entityRs = restTemplate.getForEntity(properties.getUrl(), String.class);
        var headersRs = entityRs.getHeaders();
        var bodyRs = entityRs.getBody();

        log.info("[ExecuteRiskService] Response received. Headers: " + headersRs.toString() + "\nBody: " + bodyRs);

        return bodyRs;
    }
}
