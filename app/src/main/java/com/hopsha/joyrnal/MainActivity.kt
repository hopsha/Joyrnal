package com.hopsha.joyrnal

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hopsha.joyrnal.questionnaries.Test
import com.hopsha.joyrnal.result.ResultActivity
import com.hopsha.joyrnal.test.TestActivity

class MainActivity : AppCompatActivity() {

    val startTestLauncher = registerForActivityResult(StartTestContract) { result ->
        if (result == null) {
            return@registerForActivityResult
        }
        resultLauncher.launch(result)
    }
    val resultLauncher = registerForActivityResult(ResultContract) { entryId ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        MaterialTheme {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hi, here's gonna be the graph"
                    )
                }
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxSize()
                        .padding(16.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            startTest()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add new entry"
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewMainScreen() {
        MainScreen()
    }

    private fun startTest() {
        startTestLauncher.launch(Unit)
    }

    object StartTestContract: ActivityResultContract<Unit, Test.Result?>() {

        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent(context, TestActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Test.Result? {
            if (intent == null || resultCode != Activity.RESULT_OK) {
                return null
            }
            return TestActivity.extractResult(intent)
        }
    }

    object ResultContract: ActivityResultContract<Test.Result, Int?>() {

        override fun createIntent(context: Context, input: Test.Result): Intent {
            return ResultActivity.createIntent(context, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Int? {
            if (intent == null || resultCode != Activity.RESULT_OK) {
                return null
            }
            return null
        }
    }
}