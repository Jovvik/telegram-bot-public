package bot.backend;

import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.TimeRestriction;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        new FoodDescription(
                new TimeRestriction(new TimeRestriction.Interval(1, 3)),
                new KitchenRestriction(List.of(KitchenRestriction.Kitchen.ITALIAN))).getAllRestrictions();
    }
}
