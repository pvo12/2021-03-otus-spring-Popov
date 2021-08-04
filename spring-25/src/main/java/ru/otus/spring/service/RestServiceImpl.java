package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.otus.spring.config.RestTemplatePropertiesConfig;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    private final RestTemplate restTemplate;
    private final RestTemplatePropertiesConfig config;
    private String token = "";

    private class AuthorizeInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add("Authorization", "Bearer " + token);
            return execution.execute(request, body);
        }
    }

    private RestTemplate getAuthorizedTemplate(boolean newToken) {
        if (newToken) {
            restTemplate.getInterceptors().removeIf(AuthorizeInterceptor.class::isInstance);

            token = restTemplate.postForEntity(
                    String.format("%s?username=%s&password=%s",
                            config.getTokenUrl(),
                            config.getLogin(),
                            config.getPassword()
                    ), "", String.class).getBody();

            restTemplate.getInterceptors().add(new AuthorizeInterceptor());
        }
        return restTemplate;
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) {
        try {
            return getAuthorizedTemplate(false).getForEntity(url, responseType);
        } catch (HttpClientErrorException e) {
            return getAuthorizedTemplate(true).getForEntity(url, responseType);
        }
    }
}
