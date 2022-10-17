package bot.app.service;

import bot.app.utils.data.questions.Question;
import bot.app.utils.data.questions.SpreadSheetConfig;
import bot.external.SheetsQuickstart;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionDataBase {
    private final List<Question> questions;

    public QuestionDataBase(List<SpreadSheetConfig> configs) {
        questions = new ArrayList<>();
        for (var config : configs) {
            setUpQuestions(config);
        }
    }

    private void setUpQuestions(SpreadSheetConfig config) {
        try {
            questions.addAll(SheetsQuickstart.getQuestions(config));
        } catch (Exception ignore) {}
    }

    public Question getQuestion(int i) {
        return questions.get(i);
    }

    public int numberOfQuestions() {
        return questions.size();
    }

}
