package bot.backend.services.predict;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.*;
import bot.backend.nodes.results.TimeTable;
import bot.backend.nodes.results.schema.TimeTableSchema;
import bot.backend.services.description.ActiveDescriptionService;
import bot.backend.services.description.CultureDescriptionService;
import bot.backend.services.description.DescriptionService;
import bot.backend.services.description.FoodDescriptionService;
import bot.backend.services.realworld.CultureRealWordService;
import bot.backend.services.realworld.FoodRealWorldService;
import bot.backend.services.realworld.RealWorldService;
import bot.repositories.LocationRepository;
import bot.services.LocationService;
import bot.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PredictService {

    @Configuration
    public static class PredictServiceConfig {
        @Bean
        public Map<Class<? extends Event>, DescriptionService<? extends Description<? extends Event>>>
            descriptionServices() {
            return Map.of(
                    FoodEvent.class, new FoodDescriptionService(),
                    ActiveEvent.class, new ActiveDescriptionService(),
                    CultureEvent.class, new CultureDescriptionService()
            );
        }

        @Bean
        public Map<Class<? extends Description<? extends Event>>,
                RealWorldService<? extends Event, ? extends Description<? extends Event>>>
                realWorldServices(LocationService locationService, TagService tagService) {
            return Map.of(
                    FoodDescription.class, new FoodRealWorldService(locationService, tagService),
                    CultureDescription.class, new CultureRealWordService(locationService, tagService)
            );
        }
    }

    @Autowired
    private ComposeService composeService;
    
    @Autowired
    private Map<Class<? extends Event>, DescriptionService<? extends Description<? extends Event>>>
            descriptionServices;



    @Autowired
    private Map<Class<? extends Description<? extends Event>>,
            RealWorldService<? extends Event, ? extends Description<? extends Event>>>
            realWorldServices;

    public PredictService(ComposeService composeService) {
        this.composeService = composeService;
    }


    private Map<Category, List<Description<?>>> createDescriptions(List<QuestionResult> results) {

        return null;
    }

    /**
     * Основная функция генерация
     * @param dataBlocks
     * @return итоговое расписание
     */
    public TimeTable generateTimeTable(List<QuestionResult> dataBlocks) {

        List<TimeTableSchema> acceptableSchemas = TimeTableSchema.availableSchemas().stream()
                .filter(it -> it.canUse(dataBlocks))
                .collect(Collectors.toList());

        List<TimeTable> timeTables = new ArrayList<>();

        for (TimeTableSchema timeTableSchema : acceptableSchemas) {
            var timeTable = trySchema(timeTableSchema, dataBlocks);
            if (timeTable == null) {
                continue;
            }
            timeTables.add(timeTable);
        }

        return composeService.composeTimeTable(getBestTimeTable(timeTables));

//        Map<Category, List<Description<?>>> descriptions = createDescriptions(dataBlocks);
//
//        List<Event> events = descriptions.values().stream()
//                .flatMap(List::stream)
//                .map(Description::generateEvent)
//                .collect(Collectors.toList());

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

    }

    /** Пробует составить расписание по переданной схеме и данным опроса
     *
     * @param timeTableSchema - схема мероприятия
     * @param questionResults - собранные данные
     * @return null - если не удалось, иначе не null
     */
    public TimeTable trySchema(TimeTableSchema timeTableSchema, List<QuestionResult> questionResults) {
        List<Event> resultEvents = new ArrayList<>();
        for (Class<? extends Event> eventClass : timeTableSchema.eventOrder()) {
            DescriptionService<? extends Description<? extends Event>> descriptionService =
                    descriptionServices.get(eventClass);
            List<? extends Description<? extends Event>> descriptions =
                    descriptionService// service
                    .generateDescriptions(questionResults); // description

            if (descriptions.isEmpty()) {
                return null;
            }

            Class<? extends Description> descriptionClass = descriptions.get(0).getClass();

            RealWorldService realWorldService = realWorldServices.get(descriptionClass);

            if (realWorldService == null) return null;

            List<? extends Event> events = descriptions.stream()
                    .map(d -> realWorldService.generateEvent(d))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (events.isEmpty()) {
                return null;
            }

            resultEvents.add(events.get(0)); // TODO
        }
        return new TimeTable(resultEvents);
    }

    public TimeTable getBestTimeTable(List<TimeTable> timeTables) {
        return timeTables.get(0);
    }

}
