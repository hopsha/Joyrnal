package com.hopsha.joyrnal.questionnaries.burns

import org.junit.Assert.*
import org.junit.Test

class BurnsTestTest {

    @Test
    fun verifyResultCategoryNoDepression_whenEvaluatingAnswers_givenAllAnswersNever() {
        val answers = BurnsTest.questions
            .keys
            .associateWith { BurnsTest.Answer.NEVER }

        val result = BurnsTest.evaluate(answers)

        assertEquals(BurnsTest.ResultCategory.NO_DEPRESSION, result.category)
    }

    @Test
    fun verifyResultScoreZero_whenEvaluatingAnswers_givenAllAnswersNever() {
        val answers = BurnsTest.questions
            .keys
            .associateWith { BurnsTest.Answer.NEVER }

        val result = BurnsTest.evaluate(answers)

        assertEquals(0, result.score)
    }

    @Test
    fun verifyResultCategoryExtremeDepression_whenEvaluatingAnswers_givenAllAnswersVeryOften() {
        val answers = BurnsTest.questions
            .keys
            .associateWith { BurnsTest.Answer.VERY_OFTEN }

        val result = BurnsTest.evaluate(answers)

        assertEquals(BurnsTest.ResultCategory.EXTREME_DEPRESSION, result.category)
    }

    @Test
    fun verifyResultScoreMax_whenEvaluatingAnswers_givenAllAnswersVeryOften() {
        val answers = BurnsTest.questions
            .keys
            .associateWith { BurnsTest.Answer.VERY_OFTEN }

        val result = BurnsTest.evaluate(answers)

        assertEquals(100, result.score)
    }
}