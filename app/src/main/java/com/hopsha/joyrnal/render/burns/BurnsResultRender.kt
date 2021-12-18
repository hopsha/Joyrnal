package com.hopsha.joyrnal.render.burns

import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest.ResultCategory.*
import com.hopsha.joyrnal.render.ResultRender

class BurnsResultRender: ResultRender<BurnsTest.Result> {
    override fun render(container: FrameLayout, result: BurnsTest.Result) {
        container.addView(
            LinearLayout(container.context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                TextView(container.context).apply {
                    text = result.score.toString()
                    gravity = Gravity.CENTER_VERTICAL or Gravity.START
                    textSize = 20f
                    setPadding(20)
                }
                TextView(container.context).apply {
                    text = getCategoryTitle(result.category)
                    gravity = Gravity.CENTER_VERTICAL or Gravity.START
                    textSize = 16f
                    setPadding(20)
                }
            },
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
    }

    private fun getCategoryTitle(category: BurnsTest.ResultCategory): String {
        return when(category) {
            NO_DEPRESSION -> "Депрессия отсутствует"
            NORMAL_BUT_UNHAPPY -> "Нормальное, но несчастливое состояние"
            FAINT_DEPRESSION -> "Слабо выраженная депрессия"
            MODERATE_DEPRESSION -> "Умеренная депрессия"
            STRONG_DEPRESSION -> "Сильно выраженная депрессия"
            EXTREME_DEPRESSION -> "Крайняя степень депрессии"
        }
    }
}