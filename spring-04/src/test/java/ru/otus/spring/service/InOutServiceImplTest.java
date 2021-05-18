package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class InOutServiceImplTest {
    @Mock
    private InputStream inputStream;
    @Mock
    private PrintStream printStream;
    @InjectMocks
    private InOutServiceImpl inOutService;

    @DisplayName("Должен выводить переданную строку")
    @Test
    public void shouldPrintString() {
        inOutService.println("asdf");
        Mockito.verify(printStream).println(eq("asdf"));
    }

    @DisplayName("Должен выводить пустую строку")
    @Test
    public void shouldPrintEmptyString() {
        inOutService.println();
        Mockito.verify(printStream).println();
    }

}