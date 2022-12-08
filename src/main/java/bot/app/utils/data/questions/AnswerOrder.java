package bot.app.utils.data.questions;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@AllArgsConstructor
public enum AnswerOrder {
    ONE_ROW((answers, question) -> {
        List<List<Integer>> order = new ArrayList<>();
        order.add(new ArrayList<>());
        IntStream.range(0, answers.size()).forEach(order.get(0)::add);
        return order;
    }),
    ONE_COLUMN((answers, question) -> {
        List<List<Integer>> order = new ArrayList<>();
        IntStream.range(0, answers.size()).mapToObj(List::of).forEach(order::add);
        return order;
    }),
    TIGHT((answers, question) -> {
        List<List<Integer>> order = new ArrayList<>();
        int qLen =
                Math.max(
                        (int) (question.length() * (1 + new Random().nextDouble() / 2)),
                        answers.stream().mapToInt(String::length).max().orElse(0)
                );
        order.add(new ArrayList<>());
        for (int i = 0; i < answers.size(); i++) {
            var lastRow = order.get(order.size() - 1);
            var newLen = Stream.concat(lastRow.stream(), Stream.of(i))
                    .mapToInt(j -> answers.get(j).length())
                    .sum()
                    + 12 * lastRow.size();
            if (newLen > qLen) {
                order.add(new ArrayList<>(List.of(i)));
            } else {
                lastRow.add(i);
            }
        }
        return order;
    });

    public final BiFunction<List<String>, String, List<List<Integer>>> orderFunction;

    public List<List<Integer>> fit(List<String> answers, String question) {
        return orderFunction.apply(answers, question);
    }
}
