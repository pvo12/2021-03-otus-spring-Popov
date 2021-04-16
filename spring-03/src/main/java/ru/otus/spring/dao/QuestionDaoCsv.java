package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionDaoCsv implements QuestionDao {

    private final String csvPath;


    public QuestionDaoCsv(@Value("${csv.path}") String csvPath) {
        this.csvPath = csvPath;
    }

    public List<Question> findAll() throws QuestionLoadingException {
        List<Question> questions = new ArrayList<>();
        try {
            InputStream inputStream = new ClassPathResource(csvPath).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) continue;
                List<String> list = new LinkedList<>(Arrays.asList(line.split(",")));
                String question = list.get(0);
                list.remove(0);
                String answer = list.get(0);
                list.remove(0);
                questions.add(new Question(question, list, answer));
            }

        } catch (IOException e) {
            throw new QuestionLoadingException(e);
        }
        return questions;
    }
}
