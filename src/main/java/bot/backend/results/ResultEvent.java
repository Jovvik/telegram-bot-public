package bot.backend.results;

import java.util.List;

public class ResultEvent {

    public List<Event> events;

    public ResultEvent(List<Event> events) {
        this.events = events;
    }
}
