package bot.backend.services.realworld;

import bot.backend.nodes.description.FoodDescription;
import bot.external.maps.MapService;

import java.util.List;


public class FoodRealWorldService extends RealWorldService<FoodDescription> {

    // TODO request to bd
    @Override
    TablePredicate createPredicate(FoodDescription descriptions) {
        String request = "here need the request to bd";
        return new TablePredicate(request);
    }

}
