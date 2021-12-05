package com.hopsha.joyrnal.test.diff

import androidx.recyclerview.widget.DiffUtil
import com.hopsha.joyrnal.questionnaries.Test

class TestQuestionsDiffUtilCallback(
    private val oldQuestions: List<Test.Question>,
    private val newQuestions: List<Test.Question>,
    private val oldAnswers: Map<out Test.Question, Test.Answer>,
    private val newAnswers: Map<out Test.Question, Test.Answer>,
    private val oldAnswerSpecs: Map<out Test.Question, Test.AnswerSpec<out Test.Answer>>,
    private val newAnswerSpecs: Map<out Test.Question, Test.AnswerSpec<out Test.Answer>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldQuestions.size

    override fun getNewListSize(): Int = newQuestions.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldQuestions[oldItemPosition] == newQuestions[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuestion = oldQuestions[oldItemPosition]
        val newQuestion = newQuestions[newItemPosition]
        return oldQuestion == newQuestion
                && oldAnswers[oldQuestion] == newAnswers[newQuestion]
                && oldAnswerSpecs[oldQuestion] == newAnswerSpecs[newQuestion]
    }
}