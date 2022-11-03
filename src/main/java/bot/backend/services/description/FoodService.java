package bot.backend.services.description;

import bot.backend.Main;
import bot.backend.locations.Location;
import bot.backend.nodes.QuestionNode;
import bot.backend.nodes.categories.Category;
import bot.backend.results.Event;
import bot.backend.services.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
public class FoodService implements Service {

    private List<Location> findLocations(QuestionNode node) {
        List<Location> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> tags : node.getAnswers().entrySet()) {
            if (tags.getValue() == 0) {
                continue;
            }
            for (Location location : Main.locationsBD) {
                if (location.tags.contains(tags.getKey())) {
                    result.add(location);
                }
            }
        }
        return result;
    }

    @Override
    public Event evaluate(QuestionNode node) {
        List<Location> availableLocations = this.findLocations(node);
        return new Event(availableLocations, 0, 100, Category.FOOD);
    }
}
