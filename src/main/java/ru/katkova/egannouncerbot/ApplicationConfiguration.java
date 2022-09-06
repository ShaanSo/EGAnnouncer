package ru.katkova.egannouncerbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@SpringBootConfiguration
@ComponentScan
public class ApplicationConfiguration {
    @Bean()
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
