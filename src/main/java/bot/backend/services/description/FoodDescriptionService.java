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
        data = mergeTime(data);
        try {
            return new FoodDescription(getMapDescription(data));
        } catch(Exception e) {
            return null;
        }
    }

    private List<QuestionResult> mergeTime(List<QuestionResult> data) {
        List<TimeRestriction> times = getTypedRestrictions(
                data.stream().map(QuestionResult::getRestriction).collect(Collectors.toList()),
                TimeRestriction.class
        );
        if (times.size() < 2) {
            return data;
        }

        // TODO fix
        int minFrom = times.stream()
                .map(TimeRestriction::getTime)
                .mapToInt(Event.Time::getFrom)
                .max().orElse(0);

        int maxTo = times.stream()
                .map(TimeRestriction::getTime)
                .mapToInt(Event.Time::getTo)
                .min().orElse(24 * 60 - 1);

        TimeRestriction time = new TimeRestriction(new Event.Time(minFrom, maxTo));
        data = data.stream()
                .filter(qr -> !(qr.restriction instanceof TimeRestriction) || !times.contains(qr.restriction))
                .collect(Collectors.toList());
        data.add(new GeneratedQuestionResult(time));
        return data;
    }

    @Override
    List<FoodDescription> tryModify(FoodDescription description) {
        return null;
    }
}
