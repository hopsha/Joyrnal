package com.hopsha.joyrnal.test

import com.hopsha.joyrnal.questionnaries.Test

sealed class TestEvent {

    sealed class UI : TestEvent() {
        data class Answer<A : Test.Answer>(val answer: A) : TestEvent.UI()
        data class MoveToQuestion(val index: Int) : TestEvent.UI()
    }

    sealed class Internal : TestEvent() {

    }
}