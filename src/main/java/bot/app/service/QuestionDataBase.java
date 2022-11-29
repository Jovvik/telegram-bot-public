package bot.app.service;

import bot.app.utils.data.questions.BaseQuestion;
import bot.app.utils.data.questions.ChangeableQuestion;
import bot.external.spreadsheets.SheetsService;
import bot.external.spreadsheets.SpreadSheetConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDataBase {
    @Getter
    private final Map<Integer, BaseQuestion<?>> questionMap;

    private SheetsService sheetsService;


    public BaseQuestion<?> getQuestionById(int id) {
        BaseQuestion<?> q = questionMap.get(id);
        if (q == null) return null;
        if (q instanceof ChangeableQuestion<?>) return ((ChangeableQuestion<?>) q).copy();
        return q;
    }

    public QuestionDataBase(SheetsService sheetsService, List<SpreadSheetConfig> configs) {
        this.sheetsService = sheetsService;
        questionMap = new HashMap<>();
        for (var config : configs) {
            setUpQuestions(config);
        }
    }

    private void setUpQuestions(SpreadSheetConfig config) {
        try {
            List<BaseQuestion<?>> newQuestions = sheetsService.getQuestions(config);
            newQuestions.forEach(q -> {
                questionMap.put(q.getId(), q);
            });
            System.out.println(config.name() + " added " + newQuestions.size() + " questions");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
