package bot.external.spreadsheets;

import bot.app.utils.data.DataBlock;
import bot.app.utils.data.questions.Answer;

import java.util.function.BiFunction;

public enum SpreadSheetConfig {
    BaseQuestions("Основные", "A2:AB", DataBlock::new),
    OneDayQuestions("Один день", "A:BP", DataBlock::new),
    FoodQuestions("О еде", "A:R", DataBlock::new),
    CultureQuestions("Культурные мероприятия", "A:R", DataBlock::new),
    MainLocationQuestions("Основная локация", "A:P", DataBlock::new);
    //TimeQuestions("Время", "A1:N2", SpreadSheetUtils.timeInterpreter);

    private final String listWithData;
    private final String range;
    private final BiFunction<String, Answer<String>, DataBlock<?>> interpreter;

    SpreadSheetConfig(String listWithData, String range, BiFunction<String, Answer<String>, DataBlock<?>> interpreter) {
        this.listWithData = listWithData;
        this.range = range;
        this.interpreter = interpreter;
    }

    public String getListWithData() {
        return listWithData;
    }

    public BiFunction<String, Answer<String>, DataBlock<?>> getInterpreter() {
        return interpreter;
    }

    public String getRange() {
        return range;
    }
}
