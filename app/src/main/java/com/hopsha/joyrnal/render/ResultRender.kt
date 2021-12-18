package com.hopsha.joyrnal.render

import android.widget.FrameLayout
import com.hopsha.joyrnal.questionnaries.Test

interface ResultRender<R: Test.Result> {
    fun render(container: FrameLayout, result: R)
}