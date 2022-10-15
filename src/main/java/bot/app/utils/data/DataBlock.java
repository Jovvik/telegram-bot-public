package bot.app.utils.data;

public class DataBlock<T> {
    private final String question;
    private final T answer;

    public DataBlock(String question, T answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public T getAnswer() {
        return answer;
    }
}
