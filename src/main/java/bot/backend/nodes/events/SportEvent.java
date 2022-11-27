package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SportEvent extends ActiveEvent {
    public SportEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    @AllArgsConstructor
    public enum SportType {
        FOOTBALL("Футбол"),
        BASKETBALL("Баскетбол"),
        HOCKEY("Хоккей"),
        TENNIS("Теннис"),
        FESTIVAL("Фестиваль"),
        FAIRS("Ярмарки");

        public static Map<String, SportType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private String realName;
    }

}
