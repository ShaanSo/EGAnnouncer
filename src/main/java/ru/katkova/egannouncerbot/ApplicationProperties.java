package ru.katkova.egannouncerbot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bot")
@Getter
@Setter
public final class ApplicationProperties {

    public String username;
    public String token;
    public String scheduleCron;
    public String timeZone;
    public String url;
}
