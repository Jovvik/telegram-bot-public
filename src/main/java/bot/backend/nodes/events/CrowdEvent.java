package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CrowdEvent extends ActiveEvent {

    public CrowdEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public enum MassEventType {
        FOOTBALL, BASKETBALL, FESTIVAL, FAIR, HOCKEY, TENNIS, UNKNOWN
    }

    @AllArgsConstructor
    public enum CrowdEventType {
        FOOTBALL("Футбол"),
        BASKETBALL("Баскетбол"),
        Hockey("Хоккей"),
        TENNIS("Теннис"),
        FESTIVAL("Фестиваль"),
        FAIR("Ярмарка"),
        UNKNOWN("Что-то необычное");

        public static Map<String, CrowdEventType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;
    }

}
