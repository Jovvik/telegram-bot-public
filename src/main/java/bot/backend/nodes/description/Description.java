package bot.backend.nodes.description;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.restriction.Restriction;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Description<E extends Event> {

    public Map<String, Restriction<?>> restrictions;
    public Class<E> eventClass;

    public Description(Class<E> eventClass, Map<String, Restriction<?>> restrictions) {
        this.eventClass = eventClass;
        List<String> fields = new ArrayList<>(restrictions.keySet());
        List<String> allRequiredFields = new java.util.ArrayList<>(requiredFields());
        System.out.println(allRequiredFields);
        if (!new HashSet<>(fields).containsAll(allRequiredFields)) {
            allRequiredFields.removeAll(fields);
            throw new IllegalArgumentException("not all required restrictions provided: " + allRequiredFields);
        }
        this.restrictions = restrictions;
    }

    protected void setTimeInterval(TablePredicate predicate) {
        predicate.setTimeFrom(timeRestrictions.get(0).validValues().get(0).getFrom());

        List<TimeRestriction.Interval> lastIntervals = timeRestrictions.get(timeRestrictions.size() - 1).validValues();
        predicate.setTimeTo(lastIntervals.get(lastIntervals.size() - 1).getTo());
    protected List<String> requiredFields() {
        return Arrays.stream(eventClass.getFields())
                .filter(f -> f.isAnnotationPresent(RequiredField.class))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    public abstract E generateEvent();


}
