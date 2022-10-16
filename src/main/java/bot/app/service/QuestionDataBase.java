package bot.app.service;

import bot.app.utils.data.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionDataBase {
    private final List<Question> questions;

    public QuestionDataBase(List<String> configs) {
        questions = new ArrayList<>();
        for (var config : configs) {
            setUpQuestions(config);
        }
    }

    private void setUpQuestions(String config) {
        for (int q = 0; q < 10; q++) {
            int qNum = new Random().nextInt(100);
            questions.add(new Question(
                    "Question[" + qNum + "]?",
                    IntStream.rangeClosed(
                            1,
                            new Random().nextInt(5) + 1
                    ).mapToObj(i -> {
                        return "Answer " + (char) ((int) 'A' + i - 1);
                    }).collect(Collectors.toList())
            ));
        }
    }

    public Question getQuestion(int i) {
        return questions.get(i);
    }

    public int numberOfQuestions() {
        return questions.size();
    }

}
