package bot.backend;

import bot.backend.locations.Location;
import bot.backend.nodes.QuestionNode;
import bot.backend.nodes.categories.Category;
import bot.backend.results.Event;
import bot.backend.results.ResultEvent;
import bot.backend.services.PredictService;

import java.util.List;
import java.util.Map;

public class Main {

    // мапа тегов
    static Map<String, Integer> tagsBD = Map.of(
        "Italy", 0,
        "France", 1,
            "Japan", 2
    );

    // лист локаций
    public static List<Location> locationsBD = List.of(
            new Location(0, "Жрем в Италии", List.of(0)),
            new Location(1, "Евразiya", List.of(0, 2)),
            new Location(2, "Japan", List.of(2)),
            new Location(3, "Viva la France", List.of(1))
    );

    // мапим индекс тега c желанием его получить
    static Map<Integer, Integer> answers = Map.of(
        0, 1,
        1, 0,
            2, 1
    );

    // мапим индекс тега c желанием его получить
    static Map<Integer, Integer> answers2 = Map.of(
            0, 1,
            2, 1,
            1, 1
    );

    public static void main(String[] args) {
        List<QuestionNode> nodes = List.of(
                new QuestionNode(Category.FOOD, answers),
                new QuestionNode(Category.FOOD, answers2)
        );

        PredictService predictService = new PredictService();
        predictService.fit(nodes);
        ResultEvent resultEvent = predictService.predict();
        StringBuilder res = new StringBuilder();
        for (Event event: resultEvent.events) {
            for (Location location : event.locations) {
                res.append(location.name).append("\n");
            }
        }
        System.out.println(res);
    }
}
