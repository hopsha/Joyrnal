package com.hopsha.joyrnal.test.question

import com.hopsha.joyrnal.questionnaries.Test

sealed class QuestionEvent {

    data class Answer(val answer: Test.Answer): QuestionEvent()
}