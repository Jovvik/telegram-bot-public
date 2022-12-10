package data;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TestQuestionData {
    public static List<Object> BASIC_CHOOSE_QUESTION_DATA = List.of(
            "2",
            "Choose",
            "Каков ваш бюджет на мероприятие на человека?",
            "applyBudget",
            "parseBudget",
            "0-200₽;5",
            "200-500₽;5",
            "500-1000₽;5",
            "1000-1500₽;5",
            "1500-3000₽;5",
            "3000-7000₽;5",
            "Без ограничений;5",
            "Пропустить;5"
    );

    public static List<Object> BASIC_SLIDER_QUESTION_DATA = List.of(
            "7",
            "Slider",
            "Выберите день",
            "applyDate",
            "parseDate",
            "-1d",
            "10.12.2022",
            "+1d",
            "plusMinusDay",
            "8"
    );
}
