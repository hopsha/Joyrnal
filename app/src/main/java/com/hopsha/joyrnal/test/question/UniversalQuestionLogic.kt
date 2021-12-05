package com.hopsha.joyrnal.test.question

import com.hopsha.joyrnal.questionnaries.Test
import com.spotify.mobius.Next
import com.spotify.mobius.Next.next

class UniversalQuestionLogic<Q: Test.Question, A: Test.Answer>(
    private val question: Q,
    private val answerSpec: Test.AnswerSpec<A>,
) : QuestionLogic {

    private var answer: A? = null

    override val initialState: QuestionState
        get() = QuestionState(
            question = question,
            answerSpec = answerSpec,
            answer = null
        )

    override fun update(
        model: QuestionState,
        event: QuestionEvent
    ): Next<QuestionState, QuestionEffect> {
        return when(event) {
            is QuestionEvent.Answer -> onAnswer(model, event)
        }
    }

    private fun onAnswer(model: QuestionState, event: QuestionEvent.Answer): Next<QuestionState, QuestionEffect> {
        answer = event.answer as A
        return next(
            model.copy(
                answer = answer
            )
        )
    }
}