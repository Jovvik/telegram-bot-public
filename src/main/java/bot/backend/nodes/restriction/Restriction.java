package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import lombok.Getter;

import java.util.List;

/**
 * Класс описывающий ограничение какого-то поля эвента
 *
 * @param <T> - класс поля эвента, который мы ограничиваем
 */
public abstract class Restriction<T> {

    public abstract boolean validate(T object);

    public abstract List<T> validValues();

    public abstract Class<? extends Event> getEventType();

    public String getFieldName() {
        String fullName = this.getClass().getSimpleName();
        String prefix = fullName.substring(0, fullName.length() - "Restriction".length());
        return Character.toLowerCase(prefix.charAt(0)) + prefix.substring(1);
    }
}
