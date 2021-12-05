package com.hopsha.joyrnal.test

import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.questionnaries.TestExecutorFactory

class DefaultTestLogicFactory(
    private val testExecutorFactory: TestExecutorFactory
) : TestLogicFactory {
    override fun create(test: Test<*, *, *>): TestLogic {
        return UniversalTestLogic(test, testExecutorFactory)
    }
}