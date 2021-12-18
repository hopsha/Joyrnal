package com.hopsha.joyrnal.result

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.hopsha.joyrnal.R
import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.render.DefaultRenderRepository
import com.hopsha.joyrnal.render.RenderRepository
import com.hopsha.joyrnal.render.ResultRender

class ResultActivity: AppCompatActivity() {

    private val renderRepository: RenderRepository = DefaultRenderRepository
    private lateinit var result: Test.Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        result = intent.getSerializableExtra(BUNDLE_RESULT) as Test.Result
        val renderer = renderRepository.getResultRender(result::class)

        setContentView(R.layout.activity_result)
        val container = findViewById<FrameLayout>(R.id.result_container)
        renderer.render(container, result)

        findViewById<Button>(R.id.result_finish_button).setOnClickListener {
            setResult(
                Activity.RESULT_OK,
                Intent().apply {

                }
            )
        }
    }

    companion object {

        private const val BUNDLE_RESULT = "BUNDLE_RESULT"

        fun createIntent(context: Context, result: Test.Result): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(BUNDLE_RESULT, result)
            }
        }
    }
}