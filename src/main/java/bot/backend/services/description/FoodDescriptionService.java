package bot.backend.services.description;

import bot.app.utils.data.questions.Answer;
import bot.app.utils.data.questions.GeneratedQuestionResult;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.FoodDescription;
import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.TimeRestriction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FoodDescriptionService extends DescriptionService<FoodDescription> {

    public FoodDescriptionService() {
        super(FoodEvent.class);
    }

    @Override
    FoodDescription getMostCommonDescription(List<QuestionResult> data) {
        data = mergeKitchens(data);
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

    List<QuestionResult> mergeKitchens(List<QuestionResult> data) {
        List<KitchenRestriction> kitchens = getTypedRestrictions(
                data.stream()
                        .map(QuestionResult::getRestriction)
                        .collect(Collectors.toList()),
                KitchenRestriction.class
        );
        if (kitchens.size() < 2) return data;

        KitchenRestriction merge = new KitchenRestriction(new ArrayList<>());
        Set<FoodEvent.KitchenType> types = new HashSet<>();
        for (var kitchen : kitchens) {
            types.addAll(kitchen.getValue());
        }
        merge.getValue().addAll(types);
        data = data.stream().filter(qr ->
                !(qr.restriction instanceof KitchenRestriction) ||
                !kitchens.contains(qr.restriction)).collect(Collectors.toList());
        data.add(new GeneratedQuestionResult(merge));
        return data;
    }

}
