package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class KitchenRestriction extends Restriction<KitchenRestriction.KitchenType> {

    List<KitchenType> kitchens;

    @Override
    public boolean validate(KitchenType kitchen) {
        if (kitchens.contains(KitchenType.ALL)) return true;
        return kitchens.contains(kitchen);
    }

    @Override
    public List<KitchenType> validValues() {
        return kitchens;
    }

    @AllArgsConstructor
    public enum KitchenType {
        ITALIAN("Итальянская"),
        JAPANESE("Японская"),
        RUSSIAN("Русская"),
        ASIAN("Азиатская"),
        EUROPEAN("Европейская"),
        CAUCASIAN("Кавказская"),
        FRENCH("Французская"),
        THAI("Тайская"),
        CHINESE("Китайская"),
        ALL("Не принципиально");

        public final static List<KitchenType> european = List.of(ITALIAN, RUSSIAN, FRENCH);
        public final static List<KitchenType> asian = List.of(JAPANESE, THAI, CHINESE);

        public static Map<String, KitchenType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;
    }

    @Override
    public Class<? extends Event> getEventType() {
        return FoodEvent.class;
    }
}
