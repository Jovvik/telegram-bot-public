package bot.backend.services;

import bot.backend.Main;
import bot.backend.locations.Location;
import bot.backend.nodes.categories.Category;
import bot.backend.results.Event;
import bot.backend.results.ResultEvent;
import bot.backend.nodes.QuestionNode;
import bot.backend.services.description.FoodService;

import java.util.*;
import java.util.stream.Collectors;

public class PredictService {

    FoodService foodService = new FoodService();

    List<Event> eventsForQuestions;

    public PredictService() {

    }

    public void fit(List<QuestionNode> nodes) {
        eventsForQuestions = new ArrayList<>();
        for (QuestionNode node : nodes) {
            switch (node.getCategory()) {
                case FOOD: {
                    eventsForQuestions.add(foodService.evaluate(node));
                    break;
                }

            }
        }

    }

    public ResultEvent predict() {
        Map<Integer, Integer> foodLocationsCount = new HashMap<>();
        for (Event event : eventsForQuestions) {
            event.locations.forEach(it -> foodLocationsCount.put(it.id, foodLocationsCount.getOrDefault(it.id, 0) + 1));
        }
        final int maxValue = Collections.max(foodLocationsCount.values());
        List<Location> foodLocationsId = foodLocationsCount.entrySet().stream().filter(it -> it.getValue() == maxValue).map(
                it -> Main.locationsBD.get(it.getKey())
        ).collect(Collectors.toList());
        Event foodEvent = new Event(foodLocationsId, 0, 100, Category.FOOD);
        return new ResultEvent(List.of(foodEvent));
    }
}
