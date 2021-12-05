package com.hopsha.joyrnal.questionnaries

import java.io.Serializable
import kotlin.reflect.KClass

interface Test<Q: Test.Question, A: Test.Answer, R: Test.Result> {

    val questions: List<Pair<Q, AnswerSpec<A>>>

    fun evaluate(answers: Map<Q, A>): R

    interface Question: Serializable
    interface Answer: Serializable
    interface Result: Serializable

    sealed class AnswerSpec<A: Answer>(val answerClass: KClass<A>): Serializable {

        class Options<A: Answer>(answerClass: KClass<A>, val options: List<A>): AnswerSpec<A>(answerClass)
    }
}