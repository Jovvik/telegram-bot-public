package bot.backend.services;

import bot.app.utils.data.DataBlock;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.results.MassEvent;
import bot.backend.services.description.DescriptionService;

import java.util.*;
import java.util.stream.Collectors;

public class PredictService {

    private final ComposeService composeService;
    
    private Map<Category, DescriptionService<?>> descriptionServices;

    public PredictService(ComposeService composeService) {
        this.composeService = composeService;
    }

    private Map<Category, List<Description<?>>> createDescriptions(List<DataBlock<?>> dataBlocks) {
        Map<Category, List<Description<?>>> resultMap = new HashMap<>();
        Map<Category, List<DataBlock<?>>> splitBlocks = dataBlocks.stream().collect(Collectors.groupingBy(
                DataBlock::getCategory, Collectors.toList()
                ));
        splitBlocks.forEach((category, blocks) ->
                resultMap.put(category,
                        (List<Description<?>>) descriptionServices.get(category).generateDescriptions(blocks)));
        return resultMap;
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

}
