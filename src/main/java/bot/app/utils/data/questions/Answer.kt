package bot.app.utils.data.questions

import bot.external.spreadsheets.questions.ChooseQuestionForm.AnswerCell.EdgeType

data class Answer<T>(
    var answer: T,
    val nextQuestionId: Int,
    val edgeType: EdgeType = EdgeType.Transition
) {
    constructor(answer: T, nextQuestionId: Int) :
            this(answer, nextQuestionId, EdgeType.Transition);
}