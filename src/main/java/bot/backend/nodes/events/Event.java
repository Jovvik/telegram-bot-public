package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.EventField;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public abstract class Event {

    public Location location;

    public Category category;

    @RequiredField
    public Time time;

    public Budget budget;

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
            super(from, to < from ? to + 24 * 60 : to);
        }

        private String intToTime(int time) {
            return (time < 10 ? "0" : "") + time;
        }

        private String intToTimeString(int time) {
            time = (time < 24 * 60 ? time : time - 24 * 60);
            return "**" + intToTime(time / 60) + ":" + intToTime(time % 60) + "**";
        }

        @Override
        public String toString() {
            return "c " + intToTimeString(from) + " до " + intToTimeString(to);
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

    private String prettyUrl(String url) {
        return url.split("\\?")[0];
    }

    @Override
    public String toString() {
        return "\n" +
                "**" + location.getName() + "**" +
                " | " + time + "\n" +
                prettyUrl(location.getUrl()) + "\n" +
                "__" + location.getAddress() + "__";
    }
}
