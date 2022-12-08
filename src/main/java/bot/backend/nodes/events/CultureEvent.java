package bot.backend.nodes.events;

import bot.backend.nodes.events.utils.ClassField;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CultureEvent extends OutDoorEvent {

    @RequiredField
    public CultureType type;

    public CultureEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    @AllArgsConstructor
    public enum CultureType {
        MOVIE("В кино"),
        THEATRE("В театр"),
        OPERA("Опера"),
        BALLET("Балет"),
        GALLERY("Выставка/галерея"),
        MUSICAL("На мюзикл");

        public static Map<String, CultureType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;
    }



	// generated by script at 22/06/2022 22:35:59
	public static final ClassField<CultureEvent, CultureType> TYPE = new ClassField<>(CultureEvent::getType, CultureEvent::setType, "type");
}
