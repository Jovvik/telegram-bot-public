package bot.backend.services;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.results.Event;
import bot.backend.nodes.results.MassEvent;

import java.util.*;
import java.util.stream.Collectors;

public class PredictService {

    private ComposeService composeService;

    private List<Event> events;

    public PredictService(ComposeService composeService) {
        this.composeService = composeService;
    }

    public void fillEvent(List<Description> descriptions) {

//        List<Description> filteredDescriptions = descriptions.stream().filter(description ->
//                description.getAllRestrictions().stream().allMatch(descRest ->
//                        restrictions.stream().allMatch(restriction -> restriction.validate(descRest)))
//                .collect(Collectors.toList());
    }

//    public void addEvent(List<Location> locations, Restriction restriction) {
//
//        List<Location> filteredLocations = locations.stream().filter(restriction::test).collect(Collectors.toList());
//
//        events.add(new Event(getBestLocation(filteredLocations),
//                restriction.getFrom(),
//                restriction.getTo(),
//                Category.SETTINGS));
//    }

    public MassEvent generateMassEvent() {
        return composeService.composeEvents(events);
    }

    public Location getBestLocation(List<Location> locations) {
        return locations.get(0);
    }





//    FoodService foodService = new FoodService();
//    List<Event> eventsForQuestions;

//    public void fit(List<QuestionNode> nodes) {
//        eventsForQuestions = new ArrayList<>();
//        for (QuestionNode node : nodes) {
//            switch (node.getCategory()) {
//                case FOOD: {
//                    eventsForQuestions.add(foodService.evaluate(node));
//                    break;
//                }
//
//            }
//        }
//
//    }
//
//    public ResultEvent predict() {
//        Map<Integer, Integer> foodLocationsCount = new HashMap<>();
//        for (Event event : eventsForQuestions) {
//            event.locations.forEach(it -> foodLocationsCount.put(it.id, foodLocationsCount.getOrDefault(it.id, 0) + 1));
//        }
//        final int maxValue = Collections.max(foodLocationsCount.values());
//        List<Location> foodLocationsId = foodLocationsCount.entrySet().stream().filter(it -> it.getValue() == maxValue).map(
//                it -> Main.locationsBD.get(it.getKey())
//        ).collect(Collectors.toList());
//        Event foodEvent = new Event(foodLocationsId, 0, 100, Category.FOOD);
//        return new ResultEvent(List.of(foodEvent));
//    }
}
