package bot.backend.nodes.events;

import bot.backend.nodes.events.utils.ClassField;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.utils.TypedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ActiveEvent extends OutDoorEvent {

    @RequiredField
    public List<ActiveType> activeTypes;

    public ActiveEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public ActiveEvent(Location location, Category category, Time time, List<ActiveType> activeTypes) {
        super(location, category, time);
        this.activeTypes = activeTypes;
    }

        @AllArgsConstructor
        public enum ActiveType implements TypedEnum {
            FOOTBALL("Футбол", "футбольноеполе"),
            BASKETBALL("Баскетбол", "баскетбольнаяплощадка"),
            HOCKEY("Хоккей", "каток"),
            TENNIS("Теннис", "теннисныйкорт");

            public static Map<String, ActiveType> map = new HashMap<>();

            static {
                Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
            }

            private String realName;

            @Getter
            private final String tagName;

        }

	// generated by script at 23/06/2022 18:20:08
	public static final ClassField<ActiveEvent, List<ActiveType>> ACTIVE_TYPES = new ClassField<>(ActiveEvent::getActiveTypes, ActiveEvent::setActiveTypes, "activeTypes");
}
