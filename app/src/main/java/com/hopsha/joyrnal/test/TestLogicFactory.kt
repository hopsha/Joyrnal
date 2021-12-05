package com.hopsha.joyrnal.test

import com.hopsha.joyrnal.questionnaries.Test

interface TestLogicFactory {
    fun create(test: Test<*, *, *>): TestLogic
}