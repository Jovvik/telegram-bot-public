package bot.backend.services.description;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.restriction.Restriction;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class DescriptionService<D extends Description<? extends Event>> {

    private final Class<? extends Event> eventClass;

    public List<D> generateDescriptions(List<QuestionResult> data) {
        D mostCommon = getMostCommonDescription(data);

        if (mostCommon == null) {
            return List.of();
        }

        List<D> children = tryModify(mostCommon);
        if (children == null) {
            return List.of(mostCommon);
        }

        children.add(mostCommon);
        return children;
    }

    protected Map<String, Restriction<?, ?>> getMapDescription(List<QuestionResult> data) {
        List<Restriction<?, ?>> allRestrictions = filterDescriptions(data)
                .stream()
                .map(QuestionResult::getRestriction)
                .collect(Collectors.toList());

        return allRestrictions
                .stream()
                .collect(Collectors.toMap(
                        Restriction::getFieldName,
                        Function.identity()
                ));
    }

    // best Description for DataBlocks
    abstract D getMostCommonDescription(List<QuestionResult> data);

    // try to modify current description
    // and each server know how you can change this
    // if can't modify, return null
    abstract List<D> tryModify(D description);

//    public Boolean customEquals(Description d1, Description d2) {
//        return d1.equals(d2);
//    }

//    Comparator<Description> sortOn3rdValue = new Comparator<Description>() {
//        @Override
//        public int compare(Description o1, Description o2) {
//            return o1.compare(d2);
//        }
//    };

    public List<QuestionResult> filterDescriptions(List<QuestionResult> allData) {
        return allData.stream()
                .filter(d -> d.restriction.getEventType().isAssignableFrom(eventClass))
                .collect(Collectors.toList());
    }

    public static <R extends Restriction<?, ?>> List<R> getTypedRestrictions(
            List<Restriction<?, ?>> restrictions,
            Class<R> restrictionClass) {
        return restrictions.stream()
                .filter(restrictionClass::isInstance)
                .map(restrictionClass::cast)
                .collect(Collectors.toList());
    }

}
