package bot.backend.nodes.description;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.restriction.Restriction;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Description<E extends Event> {

    public Map<String, Restriction<?, ?>> restrictions;
    public Class<E> eventClass;

    public Description(Class<E> eventClass, Map<String, Restriction<?, ?>> restrictions) {
        this.eventClass = eventClass;
        List<String> fields = new ArrayList<>(restrictions.keySet());
        List<String> allRequiredFields = new java.util.ArrayList<>(requiredFields());
        if (!new HashSet<>(fields).containsAll(allRequiredFields)) {
            allRequiredFields.removeAll(fields);
            throw new IllegalArgumentException("not all required restrictions provided: " + allRequiredFields);
        }
        this.restrictions = restrictions;
    }

    protected List<String> requiredFields() {
        return requiredFields(eventClass);
    }

    private List<String> requiredFields(Class<?> clazz) {
        return Arrays.stream(clazz.getFields())
                .filter(f -> f.isAnnotationPresent(RequiredField.class))
                .map(f -> {
                    if (f.getAnnotationsByType(RequiredField.class)[0].full()) {
                        return List.of(f.getName());
                    } else {
                        return requiredFields(f.getType())
                                .stream()
                                .map(x -> f.getName() + "." + x)
                                .collect(Collectors.toList());
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public <R extends Restriction<?, ?>> List<R> getTypedRestrictions(Class<R> restrictionClass) {
        return restrictions.values()
                .stream()
                .filter(restrictionClass::isInstance)
                .map(restrictionClass::cast)
                .collect(Collectors.toList());
    }

    public abstract E generateEvent();


}
