package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.Event.Budget;

import java.util.List;

public class BudgetRestriction extends Restriction<Budget> {

    Budget budget;

    public BudgetRestriction(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean validate(Budget object) {
        return budget.to <= object.from &&
                object.to <= budget.to;
    }

    @Override
    public List<Budget> validValues() {
        return List.of(budget);
    }

    public Class<? extends Event> getEventType() {
        return Event.class;
    }
}
