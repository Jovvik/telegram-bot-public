package bot.backend.nodes.events;

import bot.backend.nodes.events.utils.ClassField;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.utils.TypedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class CultureEvent extends OutDoorEvent {

    @RequiredField
    public Set<CultureType> cultureTypes;

    public CultureEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public CultureEvent(Location location, Category category, Time time, Set<CultureType> cultureTypes) {
        super(location, category, time);
        this.cultureTypes = cultureTypes;
    }

    @AllArgsConstructor
    public enum CultureType implements TypedEnum {
        // TODO fix to museums
        MOVIE("В кино", "кино"),
        THEATRE("В театр", "театр"),
        OPERA("Опера", "опера"),
//        BALLET("Балет" ),
        GALLERY("Выставка/галерея", "выставка"),
        MUSICAL("На мюзикл", "мюзикл");

        public static Map<String, CultureType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;

        @Getter
        private final String tagName;

        @Override
        public String getTagName() {
            return null;
        }
    }



	// generated by script at 23/06/2022 18:20:08
	public static final ClassField<CultureEvent, Set<CultureType>> CULTURE_TYPES = new ClassField<>(CultureEvent::getCultureTypes, CultureEvent::setCultureTypes, "cultureTypes");
}
