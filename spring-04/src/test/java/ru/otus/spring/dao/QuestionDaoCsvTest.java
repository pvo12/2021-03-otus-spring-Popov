package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionDaoCsvTest {
    @Autowired
    QuestionDaoCsv dao;

    @Test
    public void shouldReadQuestions() throws QuestionLoadingException {
        List<Question> list = dao.findAll();
        assertThat(list).hasSize(4);
        assertThat(list.stream().filter(question ->
                question.getQuestion().equals("2+2?")
                        && question.getAnswer().equals("4")
                        && question.getAnswers().size() == 0
        ).count()).isEqualTo(1);
        assertThat(list.stream().filter(question ->
                question.getQuestion().equals("1+4?")
                        && question.getAnswer().equals("5")
                        && question.getAnswers().size() == 2
        ).count()).isEqualTo(1);
    }
}