package com.hopsha.joyrnal.render

import com.hopsha.joyrnal.questionnaries.Test

data class RenderKit<Q: Test.Question, A: Test.Answer, R: Test.Result>(
     val questionRender: QuestionRender<Q>,
     val answersRender: AnswersRender<A>,
     val resultRender: ResultRender<R>
)