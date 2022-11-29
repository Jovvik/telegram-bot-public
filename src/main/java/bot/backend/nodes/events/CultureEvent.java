package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.CultureRestriction;

public class CultureEvent extends OutDoorEvent {

    @RequiredField
    public CultureRestriction.CultureType type;

    public CultureEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

}
