package bot.external.graphviz;

import bot.app.service.QuestionDataBase;
import bot.app.utils.data.questions.Answer;

import java.util.stream.Collectors;

public class Vizualization {
    public static String vizulize(QuestionDataBase questionDataBase) {
        StringBuilder s = new StringBuilder();
        s.append("null[color=grey];");
        for (var entry :  questionDataBase.getQuestionMap().entrySet()) {
            s.append(entry.getValue().getId()).append("[label = \"").append(entry.getValue().getQuestion()).append("\"];\n");
        }
        for (var entry :  questionDataBase.getQuestionMap().entrySet()) {
            for (var end : entry.getValue().getAnswers().stream().map(Answer::getNextQuestionId).collect(Collectors.toList())) {
                s.append(entry.getKey()).append("->").append(end < 0 ? "null" : Integer.toString(end)).append(";");
            }
        }

        return s.toString();
    }
}
