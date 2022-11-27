package bot.backend.nodes.restriction;

import java.time.LocalDate;
import java.util.List;

public class DateRestriction extends Restriction<LocalDate> {

    LocalDate date;

    public DateRestriction(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean validate(LocalDate object) {
        return date.isEqual(object);
    }

    @Override
    public List<LocalDate> validValues() {
        return null;
    }
}
