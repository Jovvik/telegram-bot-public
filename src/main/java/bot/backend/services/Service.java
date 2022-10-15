package bot.backend.services;

import bot.backend.nodes.QuestionNode;
import bot.backend.results.Event;

import java.util.List;

public interface Service {

    Event evaluate(QuestionNode node);
}
