package bot.app.utils.data.questions

data class Answer<T>(
    val answer: T,
    val nextQuestionId: Int
)