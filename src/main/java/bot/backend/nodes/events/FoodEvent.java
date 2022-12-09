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
public class FoodEvent extends Event {

    public List<KitchenType> kitchenTypes;

    @RequiredField
    public Budget budget;

    public List<FoodPlaceType> foodPlaceTypes;

    public List<FoodType> foodTypes;

    private FoodEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public FoodEvent(Location location,
                     Category category,
                     Time time,
                     List<KitchenType> kitchenTypes,
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

    @AllArgsConstructor
    public enum KitchenType implements TypedEnum {
        ITALIAN("Итальянская", "итальянскаийресторан"),
        JAPANESE("Японская", "японскийресторан"),
        RUSSIAN("Русская", "русскаякухня"),
        ASIAN("Азиатская", "азиатскийресторан"),
        EUROPEAN("Европейская", "европейскаякухня"),
        CAUCASIAN("Кавказская", "кавказскийрестоан"),
        FRENCH("Французская", "французскийресторан"),
        THAI("Тайская", "тайскаякухня"),
        CHINESE("Китайская", "китайскийресторан"),
        ALL("Не принципиально", null);

        public final static List<KitchenType> european = List.of(ITALIAN, RUSSIAN, FRENCH);
        public final static List<KitchenType> asian = List.of(JAPANESE, THAI, CHINESE);

        public static Map<String, KitchenType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private final String realName;

        @Getter
        private final String tagName;
    }


	// generated by script at 23/06/2022 18:20:08
	public static final ClassField<FoodEvent, List<KitchenType>> KITCHEN_TYPES = new ClassField<>(FoodEvent::getKitchenTypes, FoodEvent::setKitchenTypes, "kitchenTypes");
	public static final ClassField<FoodEvent, Budget> BUDGET = new ClassField<>(FoodEvent::getBudget, FoodEvent::setBudget, "budget");
	public static final ClassField<FoodEvent, List<FoodPlaceType>> FOOD_PLACE_TYPES = new ClassField<>(FoodEvent::getFoodPlaceTypes, FoodEvent::setFoodPlaceTypes, "foodPlaceTypes");
	public static final ClassField<FoodEvent, List<FoodType>> FOOD_TYPES = new ClassField<>(FoodEvent::getFoodTypes, FoodEvent::setFoodTypes, "foodTypes");
}
