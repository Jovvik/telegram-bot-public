package bot.backend.services.description;

import bot.app.utils.data.DataBlock;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.Description;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DescriptionService<D extends Description> {

    private final Category category;

    public DescriptionService(Category category) {
        this.category = category;
    }

    public List<D> generateDescriptions(List<DataBlock<?>> data) {
        D mostCommon = getMostCommonDescription(data);

        List<D> children = tryModify(mostCommon);
        if (children == null) {
            return List.of(mostCommon);
        }

        children.add(mostCommon);
        return children;
    }

    // best Description for DataBlocks
    abstract D getMostCommonDescription(List<DataBlock<?>> data);

    // try to modify current description
    // and each server know how you can change this
    // if can't modify, return null
    abstract List<D> tryModify(D description);

//    public Boolean customEquals(Description d1, Description d2) {
//        return d1.equals(d2);
//    }

//    Comparator<Description> sortOn3rdValue = new Comparator<Description>() {
//        @Override
//        public int compare(Description o1, Description o2) {
//            return o1.compare(d2);
//        }
//    };

    public List<DataBlock<?>> filterDescriptions(List<DataBlock<?>> allData) {
        return allData.stream()
                .filter(d -> d.getCategory() == category)
                .collect(Collectors.toList());
    }

}
