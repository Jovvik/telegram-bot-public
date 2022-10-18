package bot.app.utils.compressing;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BestViewTask {

    private static final int NUMBER_OF_TRIES = 20;

    public static List<List<Integer>> fit(List<String> strings, int minLength) {

        List<List<Integer>> bestResult =
                IntStream.range(0, strings.size())
                        .mapToObj(List::of)
                        .collect(Collectors.toList());

        int bestX = (int) (0.618 * Math.max(strings.stream().mapToInt(String::length).max().getAsInt(), minLength));
        int bestY = 2 * strings.size();

        for (int i = 0; i < NUMBER_OF_TRIES; i++) {
            List<Integer> newChoices = IntStream.range(0, strings.size()).boxed().collect(Collectors.toList());
            Collections.shuffle(newChoices);
            List<Integer> splitList = getRandomNumberSplit(newChoices.size());
            List<List<Integer>> candidate = new ArrayList<>();
            int currElement = 0;
            int maxX = 0;
            int bucketIndex = 0;
            for (int bucket : splitList) {
                candidate.add(new ArrayList<>());
                for (int j = 0; j < bucket; j++) {
                    candidate.get(bucketIndex).add(newChoices.get(currElement));
                    currElement++;
                }
                int currX = candidate.get(bucketIndex).stream()
                        .mapToInt(el -> strings.get(el).length())
                        .sum();
                if (currX > maxX) {
                    maxX = currX;
                }
                bucketIndex++;
            }
            int currX = (int) (0.618 * Math.max(maxX, minLength));
            int currY = 2 * candidate.size();

            boolean skipResult = false;
            for (var row : candidate) {
                int btnSize = currX / row.size();
                for (var btnId : row) {
                    if ((0.618 * strings.get(btnId).length()) > btnSize) {
                        skipResult = true;
                        break;
                    }
                }
                if (skipResult) break;
            }
            if (skipResult) continue;;

            if (minFunction(currX, currY) < minFunction(bestX, bestY)) {
                bestX = currX;
                bestY = currY;
                bestResult = candidate;
            }
        }

//        System.out.println("best ration:" + (bestX * 1d / bestY));

        return bestResult;
    }

    private static double minFunction(int x, int y) {
        return x * y;
    }

    private static List<Integer> getRandomNumberSplit(int n) {
        Random r = new Random();
        List<Integer> result = new ArrayList<>();
        while (n != 0) {
            int bucket = r.nextInt(n) + 1;
            result.add(bucket);
            n -= bucket;
        }
        return result;
    }
}
