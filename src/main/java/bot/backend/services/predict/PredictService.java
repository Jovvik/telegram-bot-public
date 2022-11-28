package bot.backend.services.predict;

import bot.app.utils.data.DataBlock;
import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.results.TimeTable;
import bot.backend.services.description.DescriptionService;

import java.util.*;
import java.util.stream.Collectors;

public class PredictService {

    private final ComposeService composeService;
    
    private Map<Category, DescriptionService<?>> descriptionServices;

    public PredictService(ComposeService composeService) {
        this.composeService = composeService;
    }

    private Map<Category, List<Description<?>>> createDescriptions(List<QuestionResult> results) {

        return null;
    }


    public TimeTable generateMassEvent(List<QuestionResult> dataBlocks) {
        Map<Category, List<Description<?>>> descriptions = createDescriptions(dataBlocks);

        List<Event> events = descriptions.values().stream()
                .flatMap(List::stream)
                .map(Description::generateEvent)
                .collect(Collectors.toList());

        // Task1:
        // List<Event> -> List<Event> но в правильном порядке (в compose event)
        // не забыть что есть compose Events которая добавляет промежутки отдыха если нужно
        //

        // Есть List<DataBlocks> на данную тему нужно найти самую похожее описание и взять его
        // getMostCommonDescription
        // tryModify пока что не трогаем

        // Есть Description generateEvent
        // нужно добавить логику чтобы по description искало event
        // по сути Description - это набор Restrictions
        // Нужно залезть в базенку и найти по данным restrictions подходящие локации
        // Время на данном этапе смотрим только глобальное, которое есть в TimeRestriction (время работы данного заведения)

        return composeService.composeEvents(events);
    }


}
