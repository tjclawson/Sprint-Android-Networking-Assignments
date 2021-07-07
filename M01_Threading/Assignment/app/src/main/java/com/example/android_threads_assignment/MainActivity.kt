package com.example.android_threads_assignment

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var counter = 0
    val tag = "THREAD_LOG"
    val task = MyAsyncTask()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        task.execute()

        //button to test responsiveness of UI
        button_test_ui.setOnClickListener {
            val color1 = resources.getColor(R.color.colorAccent)
            val color2 = resources.getColor(R.color.colorPrimary)
            if(counter == 0){
                button_test_ui.setBackgroundColor(color1)
                counter++
            } else {
                button_test_ui.setBackgroundColor(color2)
                counter--
            }
        }
    }

    inner class MyAsyncTask: AsyncTask<Unit, Int, String>(){

        override fun onPreExecute() {
            showProgressBar()
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Unit?): String {
            val primeNumbers = primes().take(16000).joinToString(", ")
            return primeNumbers
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            hideProgressBar()
            var primeString = "Primes: $result"
            tv_prime.text = primeString
        }

        override fun onCancelled() {
            super.onCancelled()
            Log.i(tag, "Thread Cancelled")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag, "Activity Stopped")
        task.cancel(true)
    }

    fun primes(): Sequence<Long> {
        var i = 0L
        return sequence {
            generateSequence { i++ }
                .filter { n -> n > 1 && ((2 until n).none { i -> n % i == 0L }) }
                .forEach { yield(it) }
        }
    }

    fun showProgressBar() {
        progress_bar.isVisible = true
        tv_prime.isVisible = false
    }

    fun hideProgressBar() {
        progress_bar.isVisible = false
        tv_prime.isVisible = true
    }
}
