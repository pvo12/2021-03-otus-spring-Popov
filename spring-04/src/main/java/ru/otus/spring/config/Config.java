package ru.otus.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "test")
@Data
public class Config {
    private Locale locale;
    private String csvPath;
    private int limit;

    public void setLocaleName(String localeName) {
        locale = Locale.forLanguageTag(localeName);
    }
}
