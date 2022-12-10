package bot.backend.nodes.events;

import bot.backend.nodes.events.utils.ClassField;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
public abstract class Event {

    public Location location;

    public Category category;

    @RequiredField
    public Time time;

    @RequiredField
    public LocalDate localDate;

    public Budget budget;

    public Event(Location location, Category category, Time time) {
        this.location = location;
        this.category = category;
        this.time = time;
    }

    @AllArgsConstructor
    private static class Limited {
        @Getter
        public Integer from;

        @Getter
        public Integer to;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Limited limited = (Limited) o;
            return Objects.equals(from, limited.from) && Objects.equals(to, limited.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
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
            return "*" + intToTime(time / 60) + ":" + intToTime(time % 60) + "*";
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
        String url = location.getUrl();
        return "\n\n" +
                "*" + location.getName() + "*" +
                " | " + time + "\n" +
                (!url.equals("") ? prettyUrl(location.getUrl()) + "\n" : "") +
                "_" + location.getAddress() + "_";
    }

	// generated by script at 23/06/2022 18:20:08
	public static final ClassField<Event, Location> LOCATION = new ClassField<>(Event::getLocation, Event::setLocation, "location");
	public static final ClassField<Event, Category> CATEGORY = new ClassField<>(Event::getCategory, Event::setCategory, "category");
	public static final ClassField<Event, Time> TIME = new ClassField<>(Event::getTime, Event::setTime, "time");
	public static final ClassField<Event, Budget> BUDGET = new ClassField<>(Event::getBudget, Event::setBudget, "budget");
	public static final ClassField<Event, LocalDate> LOCAL_DATE = new ClassField<>(Event::getLocalDate, Event::setLocalDate, "localDate");
}
