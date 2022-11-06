package bot.external.spreadsheets;

import bot.app.utils.data.DataBlock;
import java.util.function.BiFunction;

public enum SpreadSheetConfig {
    BaseQuestions("Лист1", "A2:F", DataBlock::new),
    TimeQuestions("Время", "A1:N2", SpreadSheetUtils.timeInterpreter);

    private final String listWithData;
    private final String range;
    private final BiFunction<String, String, DataBlock<?>> interpreter;

    SpreadSheetConfig(String listWithData, String range, BiFunction<String, String, DataBlock<?>> interpreter) {
        this.listWithData = listWithData;
        this.range = range;
        this.interpreter = interpreter;
    }

    public String getListWithData() {
        return listWithData;
    }

    public BiFunction<String, String, DataBlock<?>> getInterpreter() {
        return interpreter;
    }

    public String getRange() {
        return range;
    }
}
