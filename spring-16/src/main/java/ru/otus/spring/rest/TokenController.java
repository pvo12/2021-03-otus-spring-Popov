package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.service.TokenService;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public String token(@RequestParam String username, @RequestParam String password) {
        return tokenService.getToken(username, password);
    }
}
