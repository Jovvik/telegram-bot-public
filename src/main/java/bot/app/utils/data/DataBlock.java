package bot.app.utils.data;

import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
public class DataBlock<T> {

    @NonNull
    @Getter
    private final Category category;

    @NonNull
    @Getter
    private final String question;

    @Getter
    private final T answer;

    public DataBlock(String question, T answer) {
        this(Category.DEFAULT, question, answer);
    }
}
