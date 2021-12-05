package com.hopsha.joyrnal.test

import com.hopsha.joyrnal.questionnaries.Test

sealed class TestEffect {
    data class SubmitAnswer<A: Test.Answer>(val answer: A): TestEffect()
    data class MoveToQuestion<Q: Test.Question>(val question: Q): TestEffect()
}