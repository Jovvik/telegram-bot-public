package bot.app.utils.data.questions;

import bot.app.utils.data.DataBlock;

import java.util.function.BiFunction;

public enum SpreadSheetConfig {
    BaseQuestions("Лист1", "A2:B10", DataBlock::new);

    private String listWithData;
    private String range;
    private BiFunction<String, String, DataBlock<?>> interpreter;

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
