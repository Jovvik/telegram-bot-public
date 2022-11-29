package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.TypedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FoodEvent extends Event {

    public List<KitchenRestriction.KitchenType> kitchenTypes;

    @RequiredField
    public Budget budget;

    public List<FoodPlaceType> foodPlaceTypes;

    private FoodEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public FoodEvent(Location location,
                     Category category,
                     Time time,
                     List<KitchenRestriction.KitchenType> kitchenTypes,
                     Budget budget,
                     List<FoodPlaceType> foodPlaceTypes
    ) {
        super(location, category, time);
        this.kitchenTypes = kitchenTypes;
        this.budget = budget;
        this.foodPlaceTypes = foodPlaceTypes;
    }

    @AllArgsConstructor
    public enum FoodPlaceType implements TypedEnum {
        RESTAURANT("Ресторан", "ресторан"),
        BAR("Бар", "бар"),
        JUNK_FOOD("Фастфуд", "фастфуд"),
        CAFE("Кафе", "кафе");

        public static Map<String, FoodPlaceType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;

        @Getter
        private final String tagName;
    }

    @AllArgsConstructor
    public enum FoodType implements TypedEnum {
        SUSHI("Суши", "cуши"),
        BURGERS("Бургеры", "бургеры"),
        PIZZA("Пицца", "пицца"),
        MEAT("Мясо", "шашлыки"),
        FISH("Рыба", "рыбныйрестроан"),
        SHAVERMA("Шаверма", "шаверма"),
        COCKTAILS("Коктейли", "попитькоктейли");

        public static Map<String, FoodType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;

        @Getter
        private final String tagName;
    }
}
