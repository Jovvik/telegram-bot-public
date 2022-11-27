package bot.backend.nodes.restriction;

import bot.backend.nodes.events.FoodEvent.FoodType;

import java.util.List;

public class FoodTypeRestriction extends Restriction<FoodType> {
    FoodType type;

    public FoodTypeRestriction(FoodType type) {
        this.type = type;
    }

    @Override
    public boolean validate(FoodType object) {
        return object.equals(type);
    }

    @Override
    public List<FoodType> validValues() {
        return List.of(type);
    }
}
