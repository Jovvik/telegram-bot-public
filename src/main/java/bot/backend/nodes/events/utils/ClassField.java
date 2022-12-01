package bot.backend.nodes.events.utils;

import lombok.AllArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
public abstract class ClassField<C, F> {
    private Function<C, F> getter;
    private BiConsumer<C, F> setter;

    public F get(C obj) {
        return getter.apply(obj);
    }

    public void set(C obj, F value) {
        setter.accept(obj, value);
    }
}
