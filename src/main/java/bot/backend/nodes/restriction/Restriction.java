package bot.backend.nodes.restriction;

import lombok.Getter;

import java.util.List;


public abstract class Restriction<T> {

    public abstract boolean validate(T object);

    public abstract List<T> validValues();

    public interface EventType {

    }

}
