package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;

public class FoodEvent extends Event {

    public FoodEvent(Location location, Integer from, Integer to, Category category) {
        super(location, from, to, category);
    }
}