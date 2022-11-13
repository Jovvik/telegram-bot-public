package bot.backend.nodes.description;

import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.backend.nodes.results.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class Description {

    @Getter
    private TimeRestriction timeRestriction;



    public List<Restriction<?>> getAllRestrictions() {

        System.out.println(Arrays.toString(this.getClass().getFields()));

        return null;
    }

//    public Event createEvent();

}
