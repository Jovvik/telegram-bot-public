package bot.backend.services.realworld;

import bot.backend.nodes.description.FoodDescription;

import java.util.List;


public class FoodRealWorldService extends RealWorldService<FoodDescription> {

    @Override
    TablePredicate createPredicate(List<FoodDescription> descriptions) {
        return null;
    }

}
