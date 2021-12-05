package com.hopsha.joyrnal.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.test.diff.TestQuestionsDiffUtilCallback
import com.hopsha.joyrnal.test.question.QuestionFragment

class TestFragmentStateAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val questions: MutableList<Test.Question> = mutableListOf()
    private val answers: MutableMap<Test.Question, Test.Answer> = mutableMapOf()
    private val answerSpecs: MutableMap<Test.Question, Test.AnswerSpec<out Test.Answer>> = mutableMapOf()

    fun setQuestions(questions: List<Test.Question>) {
        update(newQuestions = questions)
    }

    fun setAnswers(answers: Map<out Test.Question, Test.Answer>) {
        update(newAnswers = answers)
    }

    fun setAnswerSpecs(specs: Map<out Test.Question, Test.AnswerSpec<out Test.Answer>>) {
        update(newAnswerSpecs = specs)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun createFragment(position: Int): Fragment {
        val question = questions[position]
        val answer = answers[question]
        val answerSpec = answerSpecs[question]!!

        return QuestionFragment.newInstance(question, answerSpec, answer)
    }

    private fun update(
        newQuestions: List<Test.Question> = questions,
        newAnswers: Map<out Test.Question, Test.Answer> = answers,
        newAnswerSpecs: Map<out Test.Question, Test.AnswerSpec<out Test.Answer>> = answerSpecs
    ) {
        val callback = TestQuestionsDiffUtilCallback(
            questions,
            newQuestions,
            answers,
            newAnswers,
            answerSpecs,
            newAnswerSpecs
        )
        val diff = DiffUtil.calculateDiff(callback)

        if (questions != newQuestions) {
            with(questions) {
                clear()
                addAll(newQuestions)
            }
        }

        if (answers != newAnswers) {
            with(answers) {
                clear()
                putAll(newAnswers)
            }
        }

        if (answerSpecs != newAnswerSpecs) {
            with(answerSpecs) {
                clear()
                putAll(newAnswerSpecs)
            }
        }

        diff.dispatchUpdatesTo(this)
    }
}