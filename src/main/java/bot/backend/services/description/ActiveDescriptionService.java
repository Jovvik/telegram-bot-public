package bot.backend.services.description;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.description.ActiveDescription;
import bot.backend.nodes.events.ActiveEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiveDescriptionService extends DescriptionService<ActiveDescription> {

    public ActiveDescriptionService() {
        super(ActiveEvent.class);
    }

    @Override
    public ActiveDescription getMostCommonDescription(List<QuestionResult> data) {
        return new ActiveDescription(getMapDescription(data));
    }

    @Override
    List<ActiveDescription> tryModify(ActiveDescription description) {
        return null;
    }
}
