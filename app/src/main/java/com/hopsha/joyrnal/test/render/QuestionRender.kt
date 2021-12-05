package com.hopsha.joyrnal.test.render

import android.widget.FrameLayout
import com.hopsha.joyrnal.questionnaries.Test

interface QuestionRender<Q: Test.Question> {
    fun render(container: FrameLayout, question: Q)
}