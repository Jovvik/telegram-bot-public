package bot.app.service;

import bot.app.utils.data.DataBlock;
import bot.app.utils.data.questions.Question;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PollService {
    private final Map<Long, List<DataBlock<?>>> userIdToInfos = new ConcurrentHashMap<>();
    private final Map<Long, Question> currQuestionMap = new ConcurrentHashMap<>();
    @Getter
    private final QuestionDataBase questionDataBase;
    private final EventBuilderService eventBuilderService;

    private static final int FIRST_QUESTION_ID = 2;

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
        if (qId < 0) {
            return null;
        }
        Question question = questionDataBase.getQuestionById(qId);
        currQuestionMap.put(userId, question);
        return question;
    }

    public void handleAnswer(Long userId, DataBlock<?> answer) {
        userIdToInfos.get(userId).add(answer);
    }

    public void stopPoll(Long userId) {
        List<DataBlock<?>> data = userIdToInfos.getOrDefault(userId, List.of());
        userIdToInfos.remove(userId);
        currQuestionMap.remove(userId);
        eventBuilderService.handleDataAndStartBuild(userId, data);
    }

    public boolean hasNextQuestion(Long userId) {
        if (userIdToInfos.containsKey(userId)) {
            var dataBlocks = userIdToInfos.get(userId);
            return getQuestionIdByContext(dataBlocks) > 0;
        }
        return false;
    }

    public Question currQuestion(Long usedId) {
        return currQuestionMap.get(usedId);
    }

    public List<DataBlock<?>> getUserPollInfos(Long userId) {
        return userIdToInfos.getOrDefault(userId, List.of());
    }

    public boolean existUserPollSession(Long userId) {
        return userIdToInfos.containsKey(userId);
    }

    private int getQuestionIdByContext(List<DataBlock<?>> dataBlocks) {
        if (dataBlocks.size() == 0) {
            return FIRST_QUESTION_ID;
        }
        var lastDataBlock = dataBlocks.get(dataBlocks.size() - 1);
        return lastDataBlock.getAnswer().getNextQuestionId();
    }

}
