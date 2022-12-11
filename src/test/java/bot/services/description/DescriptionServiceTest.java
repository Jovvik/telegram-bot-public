package bot.services.description;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.CultureRestriction;
import bot.backend.nodes.restriction.FoodTypeRestriction;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.services.description.CultureDescriptionService;
import bot.backend.services.description.DescriptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class DescriptionServiceTest {

	private <E extends Event, T> QuestionResult getQuestionResult(Restriction<E, T> restriction) {
		return new QuestionResult(null, null, null, restriction);

	}

	@Test
	public void filterDescriptions() {

		DescriptionService<CultureDescription> service = new CultureDescriptionService();

		List<QuestionResult> allData = List.of(
				getQuestionResult(new CultureRestriction(Collections.singleton(CultureEvent.CultureType.GALLERY))),
				getQuestionResult(new CultureRestriction(Collections.singleton(CultureEvent.CultureType.GALLERY))),
				getQuestionResult(new FoodTypeRestriction(List.of(FoodEvent.FoodType.FISH))),
				getQuestionResult(new CultureRestriction(Collections.singleton(CultureEvent.CultureType.GALLERY))),
				getQuestionResult(new KitchenRestriction(List.of((FoodEvent.KitchenType.CHINESE)))),
				getQuestionResult(new CultureRestriction(Collections.singleton(CultureEvent.CultureType.GALLERY))),
				getQuestionResult(new CultureRestriction(Collections.singleton(CultureEvent.CultureType.GALLERY)))
		);

		Assertions.assertEquals(5, service.filterDescriptions(allData).size());
	}

}
