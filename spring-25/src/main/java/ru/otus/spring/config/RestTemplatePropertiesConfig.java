package ru.otus.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rest-template")
@Getter
@Setter
public class RestTemplatePropertiesConfig {
    private int connectTimeout;
    private int readTimeout;
    private String login;
    private String password;
    private String tokenUrl;
    private String getBookUrl;
}