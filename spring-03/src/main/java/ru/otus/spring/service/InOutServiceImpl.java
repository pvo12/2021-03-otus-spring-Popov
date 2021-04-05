package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class InOutServiceImpl implements InOutService{
    private InputStream inputStream;
    private OutputStream outputStream;

    public InOutServiceImpl(@Value("#{ T(System).in}") InputStream inputStream, @Value("#{ T(System).out}") OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void println(String string) {
        new PrintStream(outputStream).println(string);
    }

    @Override
    public void println() {
        new PrintStream(outputStream).println();
    }

    @Override
    public String nextLine() {
        return new Scanner(inputStream).nextLine();
    }
}
