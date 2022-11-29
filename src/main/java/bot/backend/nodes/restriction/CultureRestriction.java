package bot.backend.nodes.restriction;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;

import java.util.List;
import java.util.Map;

public class CultureRestriction extends Restriction<CultureRestriction.CultureType> {

    List<CultureRestriction.CultureType> cultureTypes;

    public CultureRestriction(List<CultureRestriction.CultureType> cultureTypes) {
        this.cultureTypes = cultureTypes;
    }

    @Override
    public boolean validate(CultureRestriction.CultureType kitchen) {
        return cultureTypes.contains(kitchen);
    }

    @Override
    public List<CultureRestriction.CultureType> validValues() {
        return cultureTypes;
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

    public Class<? extends Event> getEventType() {
        return CultureEvent.class;
    }
}
