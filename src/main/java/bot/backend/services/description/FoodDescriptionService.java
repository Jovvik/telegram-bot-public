package bot.backend.services.description;

import bot.app.utils.data.questions.Answer;
import bot.app.utils.data.questions.GeneratedQuestionResult;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.FoodDescription;
import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.TimeRestriction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
