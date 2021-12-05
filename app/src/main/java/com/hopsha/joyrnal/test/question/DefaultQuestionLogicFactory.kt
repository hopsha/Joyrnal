package com.hopsha.joyrnal.test.question

import com.hopsha.joyrnal.questionnaries.Test

class DefaultQuestionLogicFactory : QuestionLogicFactory {

    override fun create(
        question: Test.Question,
        answerSpec: Test.AnswerSpec<*>
    ): QuestionLogic {
        return UniversalQuestionLogic(question, answerSpec)
    }
}