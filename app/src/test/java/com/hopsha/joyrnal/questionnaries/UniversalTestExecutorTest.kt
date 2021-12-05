package com.hopsha.joyrnal.questionnaries

import com.hopsha.joyrnal.questionnaries.burns.BurnsTest
import com.hopsha.joyrnal.questionnaries.Test as QTest
import org.junit.Assert.*
import org.junit.Test

class UniversalTestExecutorTest {

    @Test
    fun verifyTestIsComplete_whenAllAnswersSubmitted_givenCorrectAnswerCount() {
        val executor = UniversalTestExecutor(BurnsTest)
        executor.answerAllQuestions(BurnsTest.Answer.NEVER)

        assertTrue(executor.isComplete)
    }

    @Test
    fun verifyQuestionCountIsValid_whenTestInstantiated() {
        val executor = UniversalTestExecutor(BurnsTest)

        assertEquals(BurnsTest.questions.size, executor.questionCount)
    }

    @Test
    fun verifyResultIsAvailable_whenEvaluating_givenAllAnswersProvided() {
        val executor = UniversalTestExecutor(BurnsTest)
        executor.answerAllQuestions(BurnsTest.Answer.NEVER)

        executor.evaluate()
        assertTrue(true)
    }

    @Test
    fun verifyExceptionIsThrown_whenEvaluating_givenNotAllAnswersProvided() {
        val executor = UniversalTestExecutor(BurnsTest)
        executor.set(
            executor.questions.first(),
            BurnsTest.Answer.NEVER
        )

        assertThrows(TestExecutor.TestNotCompleteYetException::class.java) {
            executor.evaluate()
        }
    }

    private fun <Q: QTest.Question, A: QTest.Answer, R: QTest.Result>
            TestExecutor<Q, A, R>.answerAllQuestions(answer: A) {
        questions.forEach { question ->
            set(question, answer)
        }
    }
}