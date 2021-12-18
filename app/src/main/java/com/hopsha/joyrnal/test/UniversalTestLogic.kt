package com.hopsha.joyrnal.test

import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.questionnaries.TestExecutor
import com.hopsha.joyrnal.questionnaries.TestExecutorFactory
import com.spotify.mobius.Next
import com.spotify.mobius.Next.next

class UniversalTestLogic<Q: Test.Question, A: Test.Answer, R: Test.Result>(
    private val test: Test<Q, A, R>,
    testExecutorFactory: TestExecutorFactory
) : TestLogic {

    private val executor: TestExecutor<Q, A, R> = testExecutorFactory.create(test)

    override fun update(
        model: TestState,
        event: TestEvent
    ): Next<TestState, TestEffect> {
        return when(event) {
            is TestEvent.UI.Answer<*> -> onAnswer(model, event as TestEvent.UI.Answer<A>)
            is TestEvent.UI.MoveToQuestion -> onMoveToQuestion(model, event)
        }
    }

    private fun onAnswer(model: TestState, answerEvent: TestEvent.UI.Answer<A>):
            Next<TestState, TestEffect> {
        executor.set(model.currentQuestion as Q, answerEvent.answer)
        return if (executor.isComplete) {
            next(
                model.copy(
                    answers = executor.answers,
                    result = test.evaluate(executor.answers)
                )
            )
        } else {
            val nextQuestionIndex = findNextUnansweredQuestionIndex(model.currentQuestionIndex, model)
            return next(
                model.copy(
                    currentQuestionIndex = nextQuestionIndex
                )
            )
        }
    }

    private fun onMoveToQuestion(model: TestState, event: TestEvent.UI.MoveToQuestion):
            Next<TestState, TestEffect> {
        return next(
            model.copy(
                currentQuestionIndex = event.index
            )
        )
    }

    private fun findNextUnansweredQuestionIndex(answeredIndex: Int, model: TestState): Int {
        val unansweredQuestionIndexes = model.questions
            .asSequence()
            .filterNot { question ->
                model.isAnswered(question)
            }
            .mapIndexed { index, _ -> index }
            .filter { index -> index != answeredIndex }

        val firstQuestionIndexAfterAnswered = unansweredQuestionIndexes
            .filter { index -> index > answeredIndex }
            .firstOrNull()

        if (firstQuestionIndexAfterAnswered != null) {
            return firstQuestionIndexAfterAnswered
        }

        val firstQuestionIndexBeforeAnswered = unansweredQuestionIndexes
            .filter { index -> index < answeredIndex }
            .firstOrNull()

        if (firstQuestionIndexBeforeAnswered != null) {
            return firstQuestionIndexBeforeAnswered
        }

        throw IllegalStateException("WTF?")
    }

    override val initialState: TestState
        get() = TestState(
            questions = executor.questions,
            answers = emptyMap(),
            answerSpecs = executor.answerSpecs,
            currentQuestionIndex = 0
        )
}