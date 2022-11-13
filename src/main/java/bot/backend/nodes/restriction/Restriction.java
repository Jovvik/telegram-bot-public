package bot.backend.nodes.restriction;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import lombok.Getter;

import java.util.List;
import java.util.Locale;

public abstract class Restriction<T> {

    public abstract boolean validate(T object);

    public abstract List<T> validValues();
}
