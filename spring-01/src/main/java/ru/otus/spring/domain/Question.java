package ru.otus.spring.domain;

import java.util.List;

public class Question {

    private final String question;
    private final List<String> answers;

    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
