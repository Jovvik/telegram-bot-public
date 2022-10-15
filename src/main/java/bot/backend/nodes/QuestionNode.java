package bot.backend.nodes;

import bot.backend.nodes.categories.Category;

import java.util.Map;

public class QuestionNode {

    private final Category category;

    // ActivityCode -> data
    // 0-1 for Booleans
    // Integer for others
    private final Map<Integer, Integer> answers;

    public QuestionNode(Category category, Map<Integer, Integer> answers) {
        this.category = category;
        this.answers = answers;
    }

    public Category getCategory() {
        return category;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }

}
