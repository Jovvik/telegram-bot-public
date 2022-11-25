package bot.backend.nodes.restriction;

import java.util.List;

public class CultureRestriction extends Restriction<CultureRestriction.CultureType> {

    List<CultureRestriction.CultureType> cultureTypes;

    @Override
    public boolean validate(CultureRestriction.CultureType kitchen) {
        return cultureTypes.contains(kitchen);
    }

    @Override
    public List<CultureRestriction.CultureType> validValues() {
        return cultureTypes;
    }

    public enum CultureType implements EventType {
        MOVIE, THEATRE, MUSICAL
    }
}
