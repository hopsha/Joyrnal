package com.hopsha.joyrnal.test.render.burns

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.hopsha.joyrnal.R
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest.Question.*
import com.hopsha.joyrnal.test.render.QuestionRender

class BurnsQuestionRender: QuestionRender<BurnsTest.Question> {

    override fun render(container: FrameLayout, question: BurnsTest.Question) {
        val textView = TextView(container.context).apply {
            text = question.toLocalizedString(container.context)
            gravity = Gravity.CENTER
        }
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        container.removeAllViews()
        container.addView(textView, layoutParams)
    }

    private fun BurnsTest.Question.toLocalizedString(context: Context): String {
        return when(this) {
            SAD_OR_BAD_MOOD -> context.getString(R.string.burns_question_sad_or_bad_mood)
            SAD_OR_DEJECTED -> context.getString(R.string.burns_question_sad_or_dejected)
            WANT_CRY -> context.getString(R.string.burns_question_want_cry)
            GLOOM -> context.getString(R.string.burns_question_gloom)
            HOPELESSNESS -> context.getString(R.string.burns_question_hopelessness)
            LOW_SELF_ESTEEM -> context.getString(R.string.burns_question_low_self_esteem)
            NULLITY_AND_WORTHLESSNESS -> context.getString(R.string.burns_question_nullity_and_worthlessness)
            GUILTY_OR_SHAME -> context.getString(R.string.burns_question_guilty_or_shame)
            CRITIC_BLAME_YOURSELF -> context.getString(R.string.burns_question_critic_blame_yourself)
            HARD_MAKING_DECISIONS -> context.getString(R.string.burns_question_hard_making_decisions)
            LOST_INTEREST_FOR_FAMILY_FRIENDS_COLLEAGUES -> context.getString(R.string.burns_question_lost_interest_for_family_friends_colleagues)
            LONELY -> context.getString(R.string.burns_question_lonely)
            SPEND_LESS_TIME_WITH_FAMILY_FRIENDS -> context.getString(R.string.burns_question_spend_less_time_with_family_friends)
            LOSE_MOTIVATION -> context.getString(R.string.burns_question_lose_motivation)
            LOSING_INTEREST_FOR_WORK_BUSINESS -> context.getString(R.string.burns_question_losing_interest_for_work_business)
            AVOID_WORK_BUSINESS -> context.getString(R.string.burns_question_avoid_work_business)
            LOSING_JOY_MISSING_SATISFACTION_FROM_LIFE -> context.getString(R.string.burns_question_losing_joy_missing_satisfaction_from_life)
            TIRED -> context.getString(R.string.burns_question_tired)
            DIFFICULTIES_WITH_SLEEP -> context.getString(R.string.burns_question_difficulties_with_sleep)
            LOW_OR_HIGH_APPETITE -> context.getString(R.string.burns_question_low_or_high_appetite)
            LOST_INTEREST_FOR_SEX -> context.getString(R.string.burns_question_lost_interest_for_sex)
            WORRIED_ABOUT_HEALTH -> context.getString(R.string.burns_question_worried_about_health)
            SUICIDAL_THOUGHTS -> context.getString(R.string.burns_question_suicidal_thoughts)
            WANT_FINISH_LIFE -> context.getString(R.string.burns_question_want_finish_life)
            PLAN_TO_HURT_YOURSELF -> context.getString(R.string.burns_question_plan_to_hurt_yourself)
        }
    }
}