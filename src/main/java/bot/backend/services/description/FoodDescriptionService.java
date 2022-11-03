package bot.backend.services.description;

import bot.app.utils.data.DataBlock;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.description.FoodDescription;

import java.util.List;

public class FoodDescriptionService extends DescriptionService<FoodDescription> {

    public FoodDescriptionService() {
        super(Category.FOOD);
    }

    @Override
    Description getMostCommonDescription(List<DataBlock<?>> data) {
        return null;
    }

    @Override
    List<Description> tryModify(Description description) {
        return null;
    }
}