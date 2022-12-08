package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.Event.Budget;
import bot.backend.nodes.events.utils.ClassField;

import java.util.List;

public class BudgetRestriction extends Restriction<Event, Budget> {


    public BudgetRestriction(Budget value) {
        super(Event.BUDGET, value);
    }

    @Override
    public boolean validate(Budget object) {
        return getValue().to <= object.from &&
                object.to <= getValue().to;
    }

    public Class<Event> getEventType() {
        return Event.class;
    }
}
