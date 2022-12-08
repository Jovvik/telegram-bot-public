package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.ClassField;

import java.util.List;

public abstract class DiscreteRestriction<E extends Event, T> extends BaseRestriction<E, T, List<T>> {

    private int valueIndex = 0;

    public DiscreteRestriction(ClassField<E, T> field, List<T> values) {
        super(field, values);
    }

    public DiscreteRestriction(ClassField<E, T> field, T value) {
        super(value, field);
    }

    public void setValueIndex(int valueIndex) {
        if (valueIndex < 0 || values.size() <= valueIndex) {
            throw new IllegalArgumentException("no such element");
        }
    }

    @Override
    public T getValue() {
        return values.get(valueIndex);
    }

    @Override
    public List<T> valuesFromOne(T value) {
        return List.of(value);
    }
}
