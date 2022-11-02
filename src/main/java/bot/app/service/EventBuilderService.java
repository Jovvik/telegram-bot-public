package bot.app.service;

import bot.app.utils.data.DataBlock;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventBuilderService {
    private static final boolean ADD_TESTING_USERS = false;
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
    private final ConcurrentLinkedDeque<Long> waitList = new ConcurrentLinkedDeque<>();
    private final Map<Long, Future<List<Event>>> results = new ConcurrentHashMap<>();

    public EventBuilderService() {
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
    public void handleDataAndStartBuild(Long userId, List<DataBlock<?>> answerData) {
        waitList.add(userId);
        Future<List<Event>> futureResult = executor.submit(() -> {
            System.out.printf("Event for User[%s] started building%n", userId);
            waitList.removeFirst();
            try {
                Thread.sleep(5000 + new Random().nextInt(10000));
                List<Event> events = IntStream.range(0, new Random().nextInt(Math.max(answerData.size(), 2)) + 1)
                        .mapToObj(i -> {
                            int tStart = new Random().nextInt(100) + 100;
                            int tEnd = tStart + new Random().nextInt(50);
                            return new Event("Event" + i, tStart, tEnd);
                        })
                        .collect(Collectors.toList());
                System.out.printf("Event for User[%s] is done!%n", userId);
                return events;
            } catch (Exception e) {
                System.out.println("something went wrong");
                System.out.println(e.getMessage());
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

    public int queuePosition(Long userId) {
        // long but I don't care
        return Arrays.asList(waitList.toArray(new Long[0])).indexOf(userId);
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
