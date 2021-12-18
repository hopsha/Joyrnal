package com.hopsha.joyrnal.render

import com.hopsha.joyrnal.questionnaries.Test
import kotlin.reflect.KClass

interface RenderRepository {
    fun <Q: Test.Question, A: Test.Answer, R: Test.Result, T: Test<Q, A, R>> getRenderKit(testClass: KClass<T>): RenderKit<Q, A, R>
    fun <Q: Test.Question> getQuestionRender(questionClass: KClass<out Q>): QuestionRender<in Q>
    fun <A: Test.Answer> getAnswerRender(answerClass: KClass<out A>): AnswersRender<in A>
    fun <R: Test.Result> getResultRender(resultClass: KClass<out R>): ResultRender<in R>
}