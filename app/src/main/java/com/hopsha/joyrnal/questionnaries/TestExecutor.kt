package com.hopsha.joyrnal.questionnaries

import kotlin.jvm.Throws

interface TestExecutor<Q: Test.Question, A: Test.Answer, R: Test.Result> {

    val questionCount: Int
    val questions: List<Q>

    val answerSpecs: Map<Q, Test.AnswerSpec<A>>

    val answers: Map<Q, A>
    val isComplete: Boolean

    fun reset()
    fun set(question: Q, answer: A)
    fun getAnswer(question: Q): A?

    @Throws(TestNotCompleteYetException::class)
    fun evaluate(): R

    class TestNotCompleteYetException(message: String): IllegalStateException(message)
}