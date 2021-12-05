package com.hopsha.joyrnal.questionnaries

class UniversalTestExecutor<Q: Test.Question, A: Test.Answer, R: Test.Result>(
    private val test: Test<Q, A, R>
) : TestExecutor<Q, A, R> {

    override val questions: List<Q> = test.questions.map { it.first }
    override val questionCount: Int = test.questions.size

    override val answerSpecs: Map<Q, Test.AnswerSpec<A>> = test.questions.toMap()

    override val answers = mutableMapOf<Q, A>()
    private val answerCount: Int
        get() = answers.size

    override val isComplete: Boolean
        get() = questionCount == answerCount

    override fun evaluate(): R {
        if (!isComplete) {
            val unansweredQuestions = questions.toSet() - answers.keys
            throw TestExecutor.TestNotCompleteYetException(
                "Test not complete yet, so is not ready for evaluation (missing: $unansweredQuestions)"
            )
        }
        return test.evaluate(answers)
    }

    override fun reset() {
        answers.clear()
    }

    override fun set(question: Q, answer: A) {
        answers[question] = answer
    }

    override fun getAnswer(question: Q): A? {
        return answers[question]
    }
}