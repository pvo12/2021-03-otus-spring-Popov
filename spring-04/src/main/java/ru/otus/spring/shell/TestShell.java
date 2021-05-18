package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.dao.QuestionLoadingException;
import ru.otus.spring.service.TestService;

@ShellComponent
@AllArgsConstructor
public class TestShell {
    private final TestService service;

    @ShellMethod(key = "start", value = "Welcome to Test")
    public void start() throws QuestionLoadingException {
        service.test();
    }
}
