package com.example.http_operations

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_get.setOnClickListener {
            if (!isNetworkConnected()) {
                Snackbar.make(it, "No Internet Connection", Snackbar.LENGTH_LONG).show()
            } else {
                startActivity(Intent(this@MainActivity, GetPickerActivity::class.java))
            }
        }

        button_post.setOnClickListener {
            if (!isNetworkConnected()) {
                Snackbar.make(it, "No Internet Connection", Snackbar.LENGTH_LONG).show()
            } else {
                startActivity(Intent(this@MainActivity, PostActivity::class.java))
            }
        }

        button_put.setOnClickListener {
            if (!isNetworkConnected()) {
                Snackbar.make(it, "No Internet Connection", Snackbar.LENGTH_LONG).show()
            } else {
                startActivity(Intent(this@MainActivity, PutActivity::class.java))
            }
        }

        button_delete.setOnClickListener {
            if (!isNetworkConnected()) {
                Snackbar.make(it, "No Internet Connection", Snackbar.LENGTH_LONG).show()
            } else {
                startActivity(Intent(this@MainActivity, DeleteActivity::class.java))
            }
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}
