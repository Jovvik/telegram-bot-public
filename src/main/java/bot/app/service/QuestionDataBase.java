package bot.app.service;

import bot.app.utils.data.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionDataBase {
    private final List<Question> questions;

    public QuestionDataBase(List<String> configs) {
        questions = new ArrayList<>();
        for (var config : configs) {
            setUpQuestions(config);
        }
    }

    private void setUpQuestions(String config) {
        int qNum = new Random().nextInt();
        questions.add(new Question(
                "question[" + qNum + "]",
                List.of("awswer[" + qNum + "]")
        ));
    }

    public Question getQuestion(int i) {
        return questions.get(i);
    }

    public int numberOfQuestions() {
        return questions.size();
    }

}
