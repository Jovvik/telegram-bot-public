package bot.backend.services;

import bot.backend.nodes.QuestionNode;
import bot.backend.results.Event;

public interface Service {

    Event evaluate(QuestionNode node);
}
