package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SportEvent extends ActiveEvent {

    @RequiredField
    public SportEvent sportEvent;

    public SportEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    @AllArgsConstructor
    public enum SportType {
        FOOTBALL("Футбол"),
        BASKETBALL("Баскетбол"),
        HOCKEY("Хоккей"),
        TENNIS("Теннис");

        public static Map<String, SportType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private String realName;

    }

}
