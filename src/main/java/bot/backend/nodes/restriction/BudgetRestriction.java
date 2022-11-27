package bot.backend.nodes.restriction;

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
        return null;
    }
}
