package com.hopsha.joyrnal.test

import com.spotify.mobius.Update

interface TestLogic : Update<TestState, TestEvent, TestEffect> {

    val initialState: TestState
}