package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import lombok.experimental.SuperBuilder;


public class ActiveEvent extends Event {

    public ActiveEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }
}
