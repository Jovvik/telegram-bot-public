package bot.backend.results;

import bot.backend.locations.Location;
import bot.backend.nodes.categories.Category;

import java.util.List;

public class Event {
    public List<Location> locations;

    public Integer from;
    public Integer to;

    public Category category;

    public Event(List<Location> locations, Integer from, Integer to, Category category) {
        this.locations = locations;
        this.from = from;
        this.to = to;
        this.category = category;
    }
}
