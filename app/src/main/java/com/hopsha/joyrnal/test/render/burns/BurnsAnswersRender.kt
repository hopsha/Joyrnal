package com.hopsha.joyrnal.test.render.burns

import android.content.Context
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.hopsha.joyrnal.R
import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest.Answer.*
import com.hopsha.joyrnal.test.render.AnswersRender

class BurnsAnswersRender: AnswersRender<BurnsTest.Answer> {

    override fun render(
        container: FrameLayout,
        answerSpec: Test.AnswerSpec<out BurnsTest.Answer>,
        answer: BurnsTest.Answer?,
        onSubmitAnswer: (answer: BurnsTest.Answer) -> Unit
    ) {
        when(answerSpec) {
            is Test.AnswerSpec.Options -> renderOptions(container, answerSpec, answer, onSubmitAnswer)
        }
    }

    private fun renderOptions(
        container: FrameLayout,
        answerSpec: Test.AnswerSpec.Options<out BurnsTest.Answer>,
        answer: BurnsTest.Answer?,
        onSubmitAnswer: (answer: BurnsTest.Answer) -> Unit
    ) {
        container.removeAllViews()
        val listContainer = LinearLayout(container.context).apply {
            orientation = LinearLayout.VERTICAL
        }
        val linearLayoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
        )
        container.addView(listContainer, linearLayoutParams)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        ).apply {
            bottomMargin = 10
        }

        answerSpec.options
            .map { optionAnswer -> createButton(
                container.context,
                optionAnswer,
                optionAnswer == answer,
                onSubmitAnswer
            ) }
            .forEach { button ->
                listContainer.addView(button, layoutParams)
            }
    }

    private fun createButton(
        context: Context,
        optionAnswer: BurnsTest.Answer,
        isSelected: Boolean,
        onSubmitAnswer: (answer: BurnsTest.Answer) -> Unit
    ): Button {
        return Button(context).apply {
            text = optionAnswer.toLocalizedString(context)
            setOnClickListener {
                onSubmitAnswer(optionAnswer)
            }
            isEnabled = !isSelected
        }
    }

    private fun BurnsTest.Answer.toLocalizedString(context: Context): String {
        return when(this) {
            NEVER -> context.getString(R.string.burns_answer_never)
            SOMETIMES -> context.getString(R.string.burns_answer_sometimes)
            MODERATE -> context.getString(R.string.burns_answer_moderate)
            OFTEN -> context.getString(R.string.burns_answer_often)
            VERY_OFTEN -> context.getString(R.string.burns_answer_very_often)
        }
    }
}