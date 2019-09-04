package com.example.http_operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GetActivity : AppCompatActivity() {

    lateinit var employeeApi: JsonPlaceHolderApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)

        employeeApi = JsonPlaceHolderApi.Factory.create()
    }
}
