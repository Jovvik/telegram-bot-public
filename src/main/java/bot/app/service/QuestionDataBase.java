package bot.app.service;

import bot.app.utils.data.questions.Question;
import bot.external.SheetsQuickstart;
import bot.external.spreadsheets.SpreadSheetConfig;

import java.util.ArrayList;
import java.util.List;

public class QuestionDataBase {
    private final List<Question> questions;

    public Question getQuestionById(int id) {
        Question q = questionMap.get(id);
        if (q == null) return null;
        if (q instanceof ChangeableQuestion) return ((ChangeableQuestion) q).copy();
        return q;
    }

    public QuestionDataBase(List<SpreadSheetConfig> configs) {
        questions = new ArrayList<>();
        for (var config : configs) {
            setUpQuestions(config);
        }
    }

    private void setUpQuestions(SpreadSheetConfig config) {
        try {
            List<Question> newQuestions = SheetsQuickstart.getQuestions(config);
            questions.addAll(newQuestions);
            System.out.println(config.name() + " added " + newQuestions.size() + " questions");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public Question getQuestion(int i) {
        return questions.get(i);
    }

    public int numberOfQuestions() {
        return questions.size();
    }

}
