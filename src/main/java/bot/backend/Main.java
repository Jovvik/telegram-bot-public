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

    static Map<String, Integer> tagsBD = Map.of(
        "Italy", 0,
        "France", 1,
        "Japan", 2
    );

    // location list
    public static List<Location> locationsBD = List.of(
            new Location(0, "Жрем в Италии", List.of(tagsBD.get("Italy"))),
            new Location(1, "Евразiya", List.of(tagsBD.get("Italy"), tagsBD.get("Japan"))),
            new Location(2, "Japan", List.of(tagsBD.get("Japan"))),
            new Location(3, "Viva la France", List.of(tagsBD.get("France")))
    );

    // map tag index to the wish to get it
    // 1 - Yes
    // 0 - No
    static Map<Integer, Integer> answers = Map.of(
            tagsBD.get("Italy"), 1,
            tagsBD.get("France"), 0,
            tagsBD.get("Japan"), 1
    );

    // map tag index to the wish to get it
    static Map<Integer, Integer> answers2 = Map.of(
            tagsBD.get("Italy"), 1,
            tagsBD.get("Japan"), 1,
            tagsBD.get("France"), 1
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
            event.locations.forEach(loc -> res.append(loc.name).append("\n"));
        }
        System.out.println(res);
    }
}
