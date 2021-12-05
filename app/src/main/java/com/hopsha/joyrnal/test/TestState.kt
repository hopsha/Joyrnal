package com.hopsha.joyrnal.test

import com.hopsha.joyrnal.questionnaries.Test

data class TestState(
    val questions: List<Test.Question>,
    val answers: Map<out Test.Question, Test.Answer>,
    val answerSpecs: Map<out Test.Question, Test.AnswerSpec<out Test.Answer>>,
    val currentQuestionIndex: Int,
)

val TestState.currentQuestion
    get() = questions[currentQuestionIndex]

fun TestState.isAnswered(question: Test.Question): Boolean {
    return answers.containsKey(question)
}

val TestState.isComplete: Boolean
    get() = questions.size == answers.size