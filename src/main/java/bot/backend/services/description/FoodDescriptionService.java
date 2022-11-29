package bot.backend.services.description;

import bot.backend.nodes.description.FoodDescription;
import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.FoodEvent;

import java.util.List;

public class FoodDescriptionService extends DescriptionService<FoodDescription> {

    public FoodDescriptionService() {
        super(FoodEvent.class);
    }

    @Override
    FoodDescription getMostCommonDescription(List<QuestionResult> data) {
        try {
            return new FoodDescription(getMapDescription(data));
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    List<FoodDescription> tryModify(FoodDescription description) {
        return null;
    }
}
