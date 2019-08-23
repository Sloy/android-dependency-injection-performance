package com.sloydev.dependencyinjectionperformance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var job: Deferred<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            runTests()
        }

        runTests()
    }

    private fun runTests() {
        runOnUiThread {
            textView.text = getString(R.string.running_tests)
        }
        this.job = GlobalScope.async {
            InjectionTest().runTests {
                runOnUiThread {
                    textView.text = it
                }
            }
        }
    }

}
