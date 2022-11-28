package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;

public class CultureEvent extends OutDoorEvent {

    public CultureEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public enum CultureType {
        THEATRE, MOVIE, MUSICAL, OPERA, GALLERY
    }

}
