package bot.external.spreadsheets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpreadSheetConfig {
//    TestNewFunctionalityQuestion("Test1", "A2:M1000"),
    BaseQuestions("Основные", "A2:AB"),
    OneDayQuestions("Один день", "A:BP"),
    FoodQuestions("О еде", "A:R"),
    CultureQuestions("Культурные мероприятия", "A:R"),
    MainLocationQuestions("Основная локация", "A:P");
//    TimeQuestions("Время", "A1:N2", SpreadSheetUtils.timeInterpreter);

    private final String listWithData;
    private final String range;
}
