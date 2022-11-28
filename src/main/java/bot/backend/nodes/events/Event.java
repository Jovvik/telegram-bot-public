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
    private static class Limited {
        @Getter
        public Integer from;

        @Getter
        public Integer to;
    }

    // In minutes
    public static class Time extends Limited {
        public Time(Integer from, Integer to) {
            super(from, to);
        }
    }

    // In rubles
    public static class Budget extends Limited {
        public Budget(Integer from, Integer to) {
            super(from, to);
        }
    }

    // In minutes
    public static class Duration extends Limited {
        public Duration(Integer from, Integer to) {
            super(from, to);
        }
    }

}
