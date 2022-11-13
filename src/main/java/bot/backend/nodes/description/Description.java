package bot.backend.nodes.description;

import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.backend.nodes.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public abstract class Description<E extends Event> {

    @Getter
    private TimeRestriction timeRestriction;

    public List<Restriction<?>> getAllRestrictions() {

        System.out.println(Arrays.toString(this.getClass().getFields()));

        return null;
    }

    public abstract E generateEvent();

}
