package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;

public class OutDoorEvent extends Event {

    public OutDoorEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

}
