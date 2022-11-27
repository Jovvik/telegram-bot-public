package bot.backend.nodes.restriction;

import bot.backend.nodes.events.FoodEvent.Budget;

import java.util.List;

public class BudgetRestriction extends Restriction<Budget> {

    Budget lowest, highest;

    public BudgetRestriction(Budget lowest, Budget highest) {
        this.lowest = lowest;
        this.highest = highest;
    }


    @Override
    public boolean validate(Budget object) {
        return lowest.ordinal() <= object.ordinal() &&
                object.ordinal() <= highest.ordinal();
    }

    @Override
    public List<Budget> validValues() {
        return null;
    }
}
