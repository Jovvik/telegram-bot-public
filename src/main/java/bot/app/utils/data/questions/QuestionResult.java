package bot.app.utils.data.questions;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.restriction.Restriction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class QuestionResult {
    public String question;
    public Category category;
    public List<Answer<String>> answers; // if answer one == List.of(answer)
    public Restriction<?, ?> restriction;
}
