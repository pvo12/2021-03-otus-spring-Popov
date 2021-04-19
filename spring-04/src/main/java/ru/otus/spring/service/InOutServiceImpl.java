package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class InOutServiceImpl implements InOutService {
    private final InputStream inputStream;
    private final PrintStream printStream;

    public InOutServiceImpl(@Value("#{ T(System).in}") InputStream inputStream, @Value("#{ T(System).out}") PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    @Override
    public void println(String string) {
        printStream.println(string);
    }

    @Override
    public void println() {
        printStream.println();
    }

    @Override
    public String nextLine() {
        return new Scanner(inputStream).nextLine();
    }
}
