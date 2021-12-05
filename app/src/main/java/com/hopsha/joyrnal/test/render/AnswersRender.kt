package com.hopsha.joyrnal.test.render

import android.widget.FrameLayout
import com.hopsha.joyrnal.questionnaries.Test

interface AnswersRender<A: Test.Answer> {
    fun render(
        container: FrameLayout,
        answerSpec: Test.AnswerSpec<out A>,
        answer: A? = null,
        onSubmitAnswer: (answer: A) -> Unit
    )
}