package ru.otus.spring.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName(value = "Контроллер токенов должен")
class TokenControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("возвращать действующий токен")
    void shouldGetValidToken() throws Exception {
        var result = mvc.perform(post("/token?username=user&password=pass"))
                .andExpect(status().isOk())
                .andReturn();
        var token = result.getResponse().getContentAsString();

        mvc.perform(get("/api/book")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("выдавать ошибку 401")
    void shouldUnauthorized() throws Exception {
        mvc.perform(post("/token?username=user&password=pass1"))
                .andExpect(status().is4xxClientError());
        mvc.perform(post("/token?username=user1&password=pass"))
                .andExpect(status().is4xxClientError());
    }
}