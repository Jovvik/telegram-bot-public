package bot.backend.nodes.description;

import java.util.Date;

public class Description {

    @Getter
    private TimeRestriction timeRestriction;



    public List<Restriction<?>> getAllRestrictions() {

        System.out.println(Arrays.toString(this.getClass().getFields()));

        return null;
    }

//    public Event createEvent();

}
