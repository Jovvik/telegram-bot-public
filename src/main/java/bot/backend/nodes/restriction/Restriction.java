package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.ClassField;

import java.util.List;

/**
 * Класс описывающий ограничение какого-то поля эвента
 *
 * @param <T> - класс поля эвента, который мы ограничиваем
 */
public abstract class Restriction<E extends Event, T> extends DiscreteRestriction<E, T> {

    public Restriction(ClassField<E, T> field, List<T> values) {
        super(field, values);
    }

    public Restriction(ClassField<E, T> field, T value) {
        super(field, value);
    }
}
