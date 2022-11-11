package bot.backend.nodes.results;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;

public class Event {
    public Location location;

    public Integer from;
    public Integer to;

    public Category category;

    public Event(Location location, Integer from, Integer to, Category category) {
        this.location = location;
        this.from = from;
        this.to = to;
        this.category = category;
    }
}
