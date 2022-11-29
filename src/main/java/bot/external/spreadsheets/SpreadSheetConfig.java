package bot.external.spreadsheets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpreadSheetConfig {
//    TestNewFunctionalityQuestion("Test1", "A2:M1000"),
    BaseQuestions("Основные (new)", "A2:P100"),
    OneDayQuestions("Один день (new)", "A2:M100"),
    FoodQuestions("О еде (new)", "A2:N100"),
    CultureQuestions("Культурные мероприятия (new)", "A2:N100"),
    MainLocationQuestions("Основная локация (new)", "A2:L100");
//    TimeQuestions("Время", "A1:N2", SpreadSheetUtils.timeInterpreter);

    private final String listWithData;
    private final String range;
}
