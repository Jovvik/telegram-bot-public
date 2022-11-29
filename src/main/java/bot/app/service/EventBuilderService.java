package bot.app.service;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.results.TimeTable;
import bot.backend.services.predict.ComposeService;
import bot.backend.services.predict.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EventBuilderService {
    private static final boolean ADD_TESTING_USERS = false;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final ConcurrentLinkedDeque<Long> waitList = new ConcurrentLinkedDeque<>();
    private final Map<Long, Future<TimeTable>> results = new ConcurrentHashMap<>();

    @Autowired
    public PredictService predictService;

    public EventBuilderService(PredictService predictService) {
        this();
        this.predictService = predictService;
    }

    private EventBuilderService() {
        if (ADD_TESTING_USERS) {
            List<Long> users = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                users.add((long) (i * 1000 + new Random().nextInt(1000)));
                handleDataAndStartBuild(
                        users.get(i),
                        List.of());
            }
        }
    }
    public void handleDataAndStartBuild(Long userId, List<QuestionResult> answerData) {
        waitList.add(userId);
        Future<TimeTable> futureResult = executor.submit(() -> {
            System.out.printf("Event for User[%s] started building%n", userId);
            waitList.removeFirst();
            return predictService.generateTimeTable(answerData);
//            try {
//                Thread.sleep(5000 + new Random().nextInt(10000));
//                TimeTable events = new TimeTable(List.of());
//                System.out.printf("Event for User[%s] is done!%n", userId);
//                return events;
//            } catch (Exception e) {
//                System.out.println("something went wrong");
//                System.out.println(e.getMessage());
//                return null;
//            }
        });
        results.put(userId, futureResult);
    }


    public boolean isEventDone(Long userId) {
        if (results.containsKey(userId)) {
            return results.get(userId).isDone();
        }
        return false;
    }

    public int queuePosition(Long userId) {
        // long but I don't care (funny)
        return Arrays.asList(waitList.toArray(new Long[0])).indexOf(userId);
    }

    public TimeTable getResult(Long userId) {
        if (isEventDone(userId)) {
            try {
                TimeTable result = results.get(userId).get();
                results.remove(userId);
                return result;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
