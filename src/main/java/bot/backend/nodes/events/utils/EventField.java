package bot.backend.nodes.events.utils;

import bot.backend.nodes.events.Event;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class EventField<F> extends ClassField<Event, F>  {
    public EventField(
            Function<Event, F> getter,
            BiConsumer<Event, F> setter
    ) {
        super(getter, setter);
    }
}
