package ru.otus.spring.domain;

import java.util.List;

public class Question {

    private final String question;
    private final List<String> answers;
    private final String answer;

    public Question(String question, List<String> answers, String answer) {
        this.question = question;
        this.answers = answers;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getAnswer() {
        return answer;
    }
}
