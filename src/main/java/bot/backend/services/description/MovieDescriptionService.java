package bot.backend.services.description;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.description.MovieDescription;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.MovieEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieDescriptionService extends DescriptionService<MovieDescription> {

    public MovieDescriptionService() {
        super(MovieEvent.class);
    }

    @Override
    MovieDescription getMostCommonDescription(List<QuestionResult> data) {
        try {
            return new MovieDescription(getMapDescription(data));
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    List<MovieDescription> tryModify(MovieDescription description) {
        return null;
    }
}
