package com.hopsha.joyrnal.test.question

import com.hopsha.joyrnal.questionnaries.Test

interface QuestionLogicFactory {

    fun create(question: Test.Question, answerSpec: Test.AnswerSpec<*>): QuestionLogic
}