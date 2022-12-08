package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.ClassField;
import lombok.AllArgsConstructor;

/**
 *
 * @param <E> event type
 * @param <T> field type
 * @param <C> collection of values type
 */
@AllArgsConstructor
public abstract class BaseRestriction<E extends Event, T, C> {
    protected ClassField<E, T> field;
    protected C values;

    public BaseRestriction(T value, ClassField<E, T> field) {
        this.field = field;
        this.values = valuesFromOne(value);
    }

    public abstract boolean validate(T candidate);
    public abstract Class<E> getEventType();
    public abstract C valuesFromOne(T value);
    public abstract T getValue();

    public void setValue(E event) {
        if (field == null) throw new UnsupportedOperationException("no field to set");
        field.set(event, getValue());
    }

    public C validValues() {
        return values;
    }

    public String getFieldName() {
        if (field == null) return null;
        return field.name();
    }

}
