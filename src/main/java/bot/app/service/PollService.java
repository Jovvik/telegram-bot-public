package bot.app.service;

import bot.app.utils.data.DataBlock;
import bot.app.utils.data.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class PollService {
    private final Map<Long, List<DataBlock<?>>> userIdToInfos = new ConcurrentHashMap<>();
    private final QuestionDataBase questionDataBase;
    private final EventBuilderService eventBuilderService;

    public PollService(
            QuestionDataBase questionDataBase,
            EventBuilderService eventBuilderService
    ) {
        this.questionDataBase = questionDataBase;
        this.eventBuilderService = eventBuilderService;
    }

    public Question getQuestionForUser(Long userId) {
        if (!userIdToInfos.containsKey(userId)) {
            userIdToInfos.put(userId, new ArrayList<>());
        }
        int qId = getQuestionIdByContext(userIdToInfos.get(userId));
        return questionDataBase.getQuestion(qId);
    }

    public void handleAnswer(Long userId, DataBlock<?> answer) {
        userIdToInfos.get(userId).add(answer);
    }

    public void stopPoll(Long userId) {
        List<DataBlock<?>> data = userIdToInfos.getOrDefault(userId, List.of());
        userIdToInfos.remove(userId);
        eventBuilderService.handleDataAndStartBuild(data);
    }


    private int getQuestionIdByContext(List<DataBlock<?>> dataBlocks) {
        return new Random().nextInt(questionDataBase.numberOfQuestions());
    }

}
