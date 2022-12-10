package bot.external.spreadsheets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpreadSheetConfig {
    BaseQuestions("Основные (new)", "A2:P100"),
    OneDayQuestions("Один день (new)", "A2:M100"),
    FoodQuestions("О еде (new)", "A2:N100"),
    CultureQuestions("Культурные мероприятия (new)", "A2:N100"),
    MainLocationQuestions("Основная локация (new)", "A2:L100");

    private final String listWithData;
    private final String range;
}
