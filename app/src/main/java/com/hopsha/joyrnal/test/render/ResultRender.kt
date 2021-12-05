package com.hopsha.joyrnal.test.render

import android.widget.FrameLayout
import com.hopsha.joyrnal.questionnaries.Test

interface ResultRender<R: Test.Result> {
    fun render(container: FrameLayout, result: R)
}