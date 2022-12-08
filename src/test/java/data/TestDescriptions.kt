package data

import bot.backend.nodes.description.FoodDescription
import bot.backend.nodes.events.Event
import bot.backend.nodes.events.FoodEvent
import bot.backend.nodes.events.FoodEvent.FoodPlaceType
import bot.backend.nodes.restriction.BudgetRestriction
import bot.backend.nodes.restriction.FoodPlaceTypeRestriction
import bot.backend.nodes.restriction.KitchenRestriction
import bot.backend.nodes.restriction.TimeRestriction

object TestDescriptions {
    val foodDescription: FoodDescription = FoodDescription(
        mapOf(
            "time" to TimeRestriction(Event.Time(0, 1)),
            "budget" to BudgetRestriction(Event.Budget(0, Integer.MAX_VALUE)),
            "foodPlaceType" to FoodPlaceTypeRestriction(listOf(FoodPlaceType.JUNK_FOOD)),
            "kitchenInfo" to KitchenRestriction(listOf(FoodEvent.KitchenType.EUROPEAN))
        )
    );
}