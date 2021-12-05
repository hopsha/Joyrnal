package com.hopsha.joyrnal.test.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.hopsha.joyrnal.R
import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.test.render.*
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.disposables.Disposable
import com.spotify.mobius.rx3.RxMobius

class QuestionFragment : Fragment(R.layout.fragment_question) {

    private lateinit var question: Test.Question
    private lateinit var answerSpec: Test.AnswerSpec<out Test.Answer>
    private var answer: Test.Answer? = null

    private lateinit var loop: MobiusLoop<QuestionState, QuestionEvent, QuestionEffect>
    private var loopSubscription: Disposable? = null
    private val logicFactory: QuestionLogicFactory = DefaultQuestionLogicFactory()
    private val logic: QuestionLogic by lazy {
        logicFactory.create(question, answerSpec)
    }

    private val renderRepository: RenderRepository = DefaultRenderRepository()
    private val questionRender: QuestionRender<in Test.Question> by lazy {
        renderRepository.getQuestionRender(question::class)
    }
    private val answersRender: AnswersRender<in Test.Answer> by lazy {
        renderRepository.getAnswerRender(answerSpec.answerClass)
    }

    private var questionContainer: FrameLayout? = null
    private var answersContainer: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        question = requireArguments().getSerializable(BUNDLE_QUESTION) as Test.Question
        answerSpec = requireArguments().getSerializable(BUNDLE_ANSWER_SPEC) as Test.AnswerSpec<out Test.Answer>
        answer = requireArguments().getSerializable(BUNDLE_ANSWER) as Test.Answer?

        loop = RxMobius.loop(
            logic,
            RxMobius.subtypeEffectHandler<QuestionEffect, QuestionEvent>().build()
        ).startFrom(initialState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also { view ->
            questionContainer = view.findViewById(R.id.container_question)
            answersContainer = view.findViewById(R.id.container_answers)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        questionContainer = null
        answersContainer = null
    }

    override fun onStart() {
        super.onStart()
        loopSubscription = loop.observe { state ->
            view?.post {
                render(state)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        loopSubscription?.dispose()
        loopSubscription = null
    }

    private fun render(state: QuestionState) {
        questionContainer?.let { container ->
            questionRender.render(container, question)
        }
        answersContainer?.let { container ->
            answersRender.render(
                container,
                answerSpec,
                state.answer
            ) { answer ->
                loop.dispatchEvent(QuestionEvent.Answer(answer as Test.Answer))
            }
        }
    }

    private val initialState: QuestionState
        get() = QuestionState(question, answerSpec, answer)

    companion object {
        private const val BUNDLE_QUESTION = "BUNDLE_QUESTION"
        private const val BUNDLE_ANSWER_SPEC = "BUNDLE_ANSWER_SPEC"
        private const val BUNDLE_ANSWER = "BUNDLE_ANSWER"

        fun newInstance(
            question: Test.Question,
            answerSpec: Test.AnswerSpec<out Test.Answer>,
            answer: Test.Answer? = null
        ): QuestionFragment {
            val arguments = bundleOf(
                BUNDLE_QUESTION to question,
                BUNDLE_ANSWER_SPEC to answerSpec,
                BUNDLE_ANSWER to answer,
            )
            return QuestionFragment().apply {
                setArguments(arguments)
            }
        }
    }
}