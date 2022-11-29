package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;


public class ActiveEvent extends OutDoorEvent {
    public ActiveEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public enum ActivityType {
        SPORT, ROPE_PARK, YOGA, SNOWBOARD, RAFTING, DANCING
    }

}
