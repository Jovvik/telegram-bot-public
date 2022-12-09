package bot.backend.services.predict;

import bot.app.utils.data.questions.GeneratedQuestionResult;
import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.ActiveDescription;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.*;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.backend.nodes.results.TimeTable;
import bot.backend.nodes.results.schema.TimeTableSchema;
import bot.backend.services.description.ActiveDescriptionService;
import bot.backend.services.description.CultureDescriptionService;
import bot.backend.services.description.DescriptionService;
import bot.backend.services.description.FoodDescriptionService;
import bot.backend.services.realworld.ActiveRealWordService;
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
                    CultureDescription.class, new CultureRealWordService(locationService, tagService),
                    ActiveDescription.class, new ActiveRealWordService(locationService, tagService)
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

        var fixedDataBlocks = mergeTime(dataBlocks);

        List<TimeTableSchema> acceptableSchemas = TimeTableSchema.availableSchemas().stream()
                .filter(it -> it.canUse(fixedDataBlocks))
                .collect(Collectors.toList());

        List<TimeTable> timeTables = new ArrayList<>();

        for (TimeTableSchema timeTableSchema : acceptableSchemas) {
            var timeTable = trySchema(timeTableSchema, fixedDataBlocks);
            if (timeTable == null) {
                continue;
            }
            timeTables.add(timeTable);
        }

        return getBestTimeTable(timeTables);
    }

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

    /** Пробует составить расписание по переданной схеме и данным опроса
     *
     * @param timeTableSchema - схема мероприятия
     * @param questionResults - собранные данные
     * @return null - если не удалось, иначе не null
     */
    public TimeTable trySchema(TimeTableSchema timeTableSchema, List<QuestionResult> questionResults) {
        List<Event> resultEvents = new ArrayList<>();

        Event.Time tr = questionResults.stream()
                .map(QuestionResult::getRestriction)
                .filter(Objects::nonNull)
                .filter(TimeRestriction.class::isInstance)
                .map(TimeRestriction.class::cast)
                .collect(Collectors.toList()).get(0).getValue();



        for (Class<? extends Event> eventClass : timeTableSchema.eventOrder()) {
            try {
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

                List<Event> events = new ArrayList<>();

                for (int i = 0; i < descriptions.size(); i++) {
                    Event event = realWorldService.generateEvent(descriptions.get(i));
                    if (event == null) continue;
                    events.add(event);
                }

                if (events.isEmpty()) {
                    return null;
                }

                resultEvents.add(events.get(0)); // TODO
            } catch (Exception e) {
                return null;
            }
        }


        return timeTableSchema.compose(new TimeTable(resultEvents), tr).getTimeTable();
    }

    public TimeTable getBestTimeTable(List<TimeTable> timeTables) {
        return timeTables.get(0); // TODO
    }

    private static List<QuestionResult> mergeTime(List<QuestionResult> data) {
        List<TimeRestriction> times = DescriptionService.getTypedRestrictions(
                data.stream().map(QuestionResult::getRestriction).collect(Collectors.toList()),
                TimeRestriction.class
        );
        if (times.size() < 2) {
            return data;
        }

        // TODO fix
        int minFrom = times.stream()
                .map(TimeRestriction::getValue)
                .mapToInt(Event.Time::getFrom)
                .max().orElse(0);

        int maxTo = times.stream()
                .map(TimeRestriction::getValue)
                .mapToInt(Event.Time::getTo)
                .min().orElse(24 * 60 - 1);

        TimeRestriction time = new TimeRestriction(new Event.Time(minFrom, maxTo));
        data = data.stream()
                .filter(qr -> !(qr.restriction instanceof TimeRestriction) || !times.contains(qr.restriction))
                .collect(Collectors.toList());
        data.add(new GeneratedQuestionResult(time));
        return data;
    }

}
