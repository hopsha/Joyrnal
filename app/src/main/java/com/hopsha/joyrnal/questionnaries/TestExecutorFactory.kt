package com.hopsha.joyrnal.questionnaries

interface TestExecutorFactory {

    fun <Q: Test.Question, A: Test.Answer, R: Test.Result> create(test: Test<Q, A, R>): TestExecutor<Q, A, R>
}