package bot.backend.services.description;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.CultureEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CultureDescriptionService extends DescriptionService<CultureDescription> {

    public CultureDescriptionService() {
        super(CultureEvent.class);
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
