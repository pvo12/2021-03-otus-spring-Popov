package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.Config;

@AllArgsConstructor
@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;
    private final Config config;

    @Override
    public String getMessage(String var) {
        return messageSource.getMessage(var, new String[]{}, config.getLocale());
    }
}
