package ru.katkova.egannouncerbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.katkova.egannouncerbot.service.EpicGamesService;

@SpringBootConfiguration
public class ApplicationConfiguration {
    @Bean()
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
