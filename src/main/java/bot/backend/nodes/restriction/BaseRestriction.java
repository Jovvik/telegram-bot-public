package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;

import java.util.List;

public abstract class BaseRestriction<T, C> {

    public abstract boolean validate(T object);

    public abstract C validValues();

    public abstract Class<? extends Event> getEventType();

    public String getFieldName() {
        String fullName = this.getClass().getSimpleName();
        String prefix = fullName.substring(0, fullName.length() - "Restriction".length());
        return Character.toLowerCase(prefix.charAt(0)) + prefix.substring(1);
    }
}
