package com.hopsha.joyrnal.render

import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest
import com.hopsha.joyrnal.render.burns.BurnsAnswersRender
import com.hopsha.joyrnal.render.burns.BurnsQuestionRender
import com.hopsha.joyrnal.render.burns.BurnsResultRender
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

object DefaultRenderRepository: RenderRepository {

    private val renderKits: List<RenderKitEntry<*, *, *, *>> = listOf(
        RenderKitEntry(
            RenderKit(
                BurnsQuestionRender(),
                BurnsAnswersRender(),
                BurnsResultRender()
            ),
            BurnsTest::class,
            BurnsTest.Question::class,
            BurnsTest.Answer::class,
            BurnsTest.Result::class
        )
    )

    override fun <Q : Test.Question, A : Test.Answer, R : Test.Result, T : Test<Q, A, R>> getRenderKit(
        testClass: KClass<T>
    ): RenderKit<Q, A, R> {
        return renderKits.asSequence()
            .filter { entry -> entry.testClass == testClass }
            .map { entry -> entry.renderKit }
            .filterIsInstance<RenderKit<Q, A, R>>()
            .firstOrNull()
            ?: throw IllegalArgumentException("$testClass not supported")
    }

    override fun <Q : Test.Question> getQuestionRender(questionClass: KClass<out Q>): QuestionRender<in Q> {
        return renderKits.asSequence()
            .filter { entry -> entry.questionClass == questionClass }
            .map { entry -> entry.renderKit.questionRender }
            .filterIsInstance<QuestionRender<in Q>>()
            .firstOrNull()
            ?: throw IllegalArgumentException("$questionClass not supported")
    }

    override fun <A : Test.Answer> getAnswerRender(answerClass: KClass<out A>): AnswersRender<in A> {
        return renderKits.asSequence()
            .filter { entry -> entry.answerClass == answerClass }
            .map { entry -> entry.renderKit.answersRender }
            .filterIsInstance<AnswersRender<in A>>()
            .firstOrNull()
            ?: throw IllegalArgumentException("$answerClass not supported")
    }

    override fun <R : Test.Result> getResultRender(resultClass: KClass<out R>): ResultRender<in R> {
        return renderKits.asSequence()
            .filter { entry -> entry.resultClass == resultClass }
            .map { entry -> entry.renderKit.resultRender }
            .filterIsInstance<ResultRender<in R>>()
            .firstOrNull()
            ?: throw IllegalArgumentException("$resultClass not supported")
    }

    private data class RenderKitEntry<Q : Test.Question, A : Test.Answer, R : Test.Result, T : Test<Q, A, R>>(
        val renderKit: RenderKit<Q, A, R>,
        val testClass: KClass<T>,
        val questionClass: KClass<Q>,
        val answerClass: KClass<A>,
        val resultClass: KClass<R>,
    )
}