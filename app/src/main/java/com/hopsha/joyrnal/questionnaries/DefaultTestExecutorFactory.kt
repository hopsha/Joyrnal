package com.hopsha.joyrnal.questionnaries

class DefaultTestExecutorFactory : TestExecutorFactory {
    override fun <Q : Test.Question, A : Test.Answer, R : Test.Result> create(test: Test<Q, A, R>): TestExecutor<Q, A, R> {
        return UniversalTestExecutor(test)
    }
}