package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.spring.config.RestTemplateConfig;

@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    private final RestTemplate restTemplate;
    private final RestTemplateConfig config;

    @Override
    public RestTemplate getAuthorizedTemplate() {
        restTemplate.getInterceptors()
                .add(new BasicAuthenticationInterceptor(config.getLogin(), config.getPassword()));
        String token = restTemplate.postForEntity(config.getTokenUrl(), "", String.class).getBody();
        restTemplate.getInterceptors().remove(0);
        restTemplate.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + token);
                    return execution.execute(request, body);
                });
        return restTemplate;
    }
}
