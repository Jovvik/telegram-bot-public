package bot.app.utils.data.questions;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

@AllArgsConstructor
public enum AnswerOrder {
    ONE_ROW(answers -> {
        List<List<Integer>> order = new ArrayList<>();
        order.add(new ArrayList<>());
        IntStream.range(0, answers.size()).forEach(order.get(0)::add);
        return order;
    }),
    ONE_COLUMN(answers -> {
        List<List<Integer>> order = new ArrayList<>();
        IntStream.range(0, answers.size()).mapToObj(List::of).forEach(order::add);
        return order;
    });

    public final Function<List<String>, List<List<Integer>>> orderFunction;

    public List<List<Integer>> fit(List<String> answers) {
        return orderFunction.apply(answers);
    }
}
