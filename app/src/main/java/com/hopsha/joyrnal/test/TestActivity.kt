package com.hopsha.joyrnal.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.hopsha.joyrnal.R
import com.hopsha.joyrnal.questionnaries.DefaultTestExecutorFactory
import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.questionnaries.TestExecutorFactory
import com.hopsha.joyrnal.questionnaries.burns.BurnsTest
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.disposables.Disposable
import com.spotify.mobius.rx3.RxMobius

class TestActivity : FragmentActivity() {

    private lateinit var loop: MobiusLoop<TestState, TestEvent, TestEffect>
    private var loopSubscription: Disposable? = null
    private val testExecutorFactory: TestExecutorFactory = DefaultTestExecutorFactory()
    private val logicFactory: TestLogicFactory = DefaultTestLogicFactory(testExecutorFactory)
    private val logic: TestLogic = logicFactory.create(BurnsTest)

    private lateinit var viewPager: ViewPager2
    private lateinit var buttonFinish: View
    private val fragmentAdapter = TestFragmentStateAdapter(this)

    private var result: Test.Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        buttonFinish = findViewById(R.id.test_button_finish)
        buttonFinish.setOnClickListener {
            result?.let { result ->
                onComplete(result)
            }
        }

        viewPager = findViewById(R.id.test_view_pager)
        viewPager.adapter = fragmentAdapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        loop = RxMobius.loop(
            logic,
            RxMobius.subtypeEffectHandler<TestEffect, TestEvent>().build()
        ).startFrom(logic.initialState)
    }

    override fun onStart() {
        super.onStart()
        loopSubscription = loop.observe { state ->
            fragmentAdapter.setQuestions(state.questions)
            fragmentAdapter.setAnswers(state.answers)
            fragmentAdapter.setAnswerSpecs(state.answerSpecs)

            result = state.result
            buttonFinish.isVisible = state.isComplete
        }
    }

    override fun onStop() {
        super.onStop()
        loopSubscription?.dispose()
        loopSubscription = null
    }

    private fun onComplete(result: Test.Result) {
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(BUNDLE_RESULT, result)
            }
        )
        finish()
    }

    fun onAnswer(answer: Test.Answer) {
        loop.dispatchEvent(TestEvent.UI.Answer(answer))
    }

    companion object {
        private const val BUNDLE_RESULT = "BUNDLE_RESULT"

        fun extractResult(intent: Intent): Test.Result {
            return intent.getSerializableExtra(BUNDLE_RESULT) as Test.Result
        }
    }
}