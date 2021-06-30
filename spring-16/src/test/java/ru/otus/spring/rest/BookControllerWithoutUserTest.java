package ru.otus.spring.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName(value = "Контроллер книг должен")
class BookControllerWithoutUserTest {
    @Autowired
    private MockMvc mvc;
    private static final String GET = "get";
    private static final String POST = "post";
    private static final String DELETE = "delete";
    private static final String PUT = "put";

    private static Stream<Arguments> addFixture() {
        return Stream.of(
                Arguments.of(GET, "/api/book"),
                Arguments.of(GET, "/api/book/2/comment"),
                Arguments.of(GET, "/api/book/1"),
                Arguments.of(GET, "/api/book/3"),
                Arguments.of(POST, "/api/book"),
                Arguments.of(DELETE, "/api/book/1"),
                Arguments.of(PUT, "/api/book/1")
        );
    }

    @DisplayName("возвращать ошибку 3xx")
    @ParameterizedTest
    @MethodSource("addFixture")
    void shouldReturnCorrectBooksList(String method, String url) throws Exception {
        if (method.equals(GET)) {
            mvc.perform(get(url)).andExpect(status().is3xxRedirection());
        } else if (method.equals(POST)) {
            mvc.perform(post(url)).andExpect(status().is3xxRedirection());
        } else if (method.equals(PUT)) {
            mvc.perform(put(url)).andExpect(status().is3xxRedirection());
        } else if (method.equals(DELETE)) {
            mvc.perform(delete(url)).andExpect(status().is3xxRedirection());
        }
    }
}