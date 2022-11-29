package bot.backend.services.description;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.events.ActiveEvent;

import java.util.List;

public class CultureDescriptionService extends DescriptionService<CultureDescription> {

    public CultureDescriptionService() {
        super(ActiveEvent.class);
    }

    @Override
    public CultureDescription getMostCommonDescription(List<QuestionResult> data) {
        return new CultureDescription(getMapDescription(data));
    }

    @Override
    List<CultureDescription> tryModify(CultureDescription description) {
        return null;
    }
}
