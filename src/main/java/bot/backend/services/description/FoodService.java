package bot.backend.services.description;

@Deprecated
public class FoodService implements Service {

    private List<Location> findLocations(QuestionNode node) {
        List<Location> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> tags : node.getAnswers().entrySet()) {
            if (tags.getValue() == 0) {
                continue;
            }
            for (Location location : Main.locationsBD) {
                if (location.tags.contains(tags.getKey())) {
                    result.add(location);
                }
            }
        }
        return result;
    }

    @Override
    public Event evaluate(QuestionNode node) {
        List<Location> availableLocations = this.findLocations(node);
        return new Event(availableLocations, 0, 100, Category.FOOD);
    }
}
