package com.hopsha.joyrnal.test.question

import com.hopsha.joyrnal.questionnaries.Test

data class QuestionState(
    val question: Test.Question,
    val answerSpec: Test.AnswerSpec<out Test.Answer>,
    val answer: Test.Answer?,
)