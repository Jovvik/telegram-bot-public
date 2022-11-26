package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Event {

    @Getter
    public Location location;

    @Getter
    public Category category;

    @Getter
    @RequiredField
    public Time time;

    @AllArgsConstructor
    public static class Time {
        @Getter
        public Integer from;

        @Getter
        public Integer to;
    }

}
