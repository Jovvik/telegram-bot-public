package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
            ITALIAN, JAPANESE, RUSSIAN, ASIAN, CAUCASIAN, ALL,
        }
    }

    @AllArgsConstructor
    public enum Budget {
        LOW(0, 1000),
        MIDDLE(1000, 2000),
        HIGH(2000, Integer.MAX_VALUE);

        public final int to;
        public final int from;
    }

    public enum FoodPlaceType {
        RESTAURANT, BAR, CAFE;
    }
}
