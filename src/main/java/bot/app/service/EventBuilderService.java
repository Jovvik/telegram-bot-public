package bot.app.service;

import bot.app.utils.data.DataBlock;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventBuilderService {
    public static class Event {
        int tStart;
        int tEnd;
        String name;

        public Event(String name, int tStart, int tEnd) {
            this.tStart = tStart;
            this.tEnd = tEnd;
            this.name = name;
        }

        public int getStart() {
            return tStart;
        }

        public int getEnd() {
            return tEnd;
        }

        public String getName() {
            return name;
        }
    }
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final Map<Long, Future<List<Event>>> results = new ConcurrentHashMap<>();

    public void handleDataAndStartBuild(Long userId, List<DataBlock<?>> answerData) {
        Future<List<Event>> futureResult = executor.submit(() -> {
            System.out.printf("Event for User[%s] started building%n", userId);
            try {
                Thread.sleep(10000);
                List<Event> events = IntStream.range(0, new Random().nextInt(answerData.size()) + 1)
                        .mapToObj(i -> {
                            int tStart = new Random().nextInt(100) + 100;
                            int tEnd = tStart + new Random().nextInt(50);
                            return new Event("Event" + i, tStart, tEnd);
                        })
                        .collect(Collectors.toList());
                System.out.printf("Event for User[%s] is done!%n", userId);
                return events;
            } catch (InterruptedException ignore) {
                return List.of();
            }
        });
        results.put(userId, futureResult);
    }


    public boolean isEventDone(Long userId) {
        if (results.containsKey(userId)) {
            return results.get(userId).isDone();
        }
        return false;
    }

    public List<Event> getResult(Long userId) {
        if (isEventDone(userId)) {
            try {
                List<Event> result = results.get(userId).get();
                results.remove(userId);
                return result;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
