package bot.backend.services.realworld;

import bot.backend.nodes.description.FoodDescription;
import bot.external.maps.MapService;

import java.util.List;


public class FoodRealWorldService extends RealWorldService<FoodDescription> {

    @Override
    TablePredicate createPredicate(List<FoodDescription> descriptions) { return null; }

}
