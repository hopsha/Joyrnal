package com.hopsha.joyrnal.test.question

import com.spotify.mobius.Update

interface QuestionLogic: Update<QuestionState, QuestionEvent, QuestionEffect> {

    val initialState: QuestionState
}