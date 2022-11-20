package bot.backend.nodes.restriction;

import java.util.List;

public class SportRestriction extends Restriction<SportRestriction.SportType> {

    List<SportType> sportTypes;

    @Override
    public boolean validate(SportType sportType) {
        return sportTypes.contains(sportType);
    }

    @Override
    public List<SportRestriction.SportType> validValues() {
        return sportTypes;
    }

    public enum SportType {
        MOVIE, THEATRE, MUSICAL
    }
}