package bot.backend.nodes.events;

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
public class FoodEvent extends Event {

    @RequiredField
    public KitchenInfo kitchenInfo;

    @RequiredField
    public Budget budget;

    @RequiredField
    public FoodPlaceType foodPlaceTypes;

    private FoodEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public FoodEvent(Location location,
                     Category category,
                     Time time,
                     KitchenInfo kitchenInfo,
                     Budget budget,
                     FoodPlaceType foodPlaceTypes
    ) {
        super(location, category, time);
        this.kitchenInfo = kitchenInfo;
        this.budget = budget;
        this.foodPlaceTypes = foodPlaceTypes;
    }

    @AllArgsConstructor
    @Getter
    public static class KitchenInfo {
        public Kitchen kitchen;
        public SubKitchen subKitchen;

        public enum Kitchen {
            EUROPEAN, RUSSIAN, ASIAN, CAUCASIAN, ALL,
        }

        public enum SubKitchen {
            SUSHI, PIZZA, BURGERS, MEAT, FISH, COCKTAIL
        }
    }

    @AllArgsConstructor
    public enum FoodPlaceType {
        RESTAURANT("Ресторан"),
        BAR("Бар"),
        JUNK_FOOD("Фастфуд"),
        CAFE("Кафе");

        public static Map<String, FoodPlaceType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;
    }

    @AllArgsConstructor
    public enum FoodType {
        SUSHI("Суши"),
        BURGERS("Бургеры"),
        PIZZA("Пицца"),
        MEAT("Мясо"),
        FISH("Рыба"),
        COCKTAILS("Коктейли");

        public static Map<String, FoodType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;
    }
}
