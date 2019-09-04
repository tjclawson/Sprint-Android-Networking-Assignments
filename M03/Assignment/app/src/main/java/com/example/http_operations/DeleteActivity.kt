package com.example.http_operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_get.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteActivity : AppCompatActivity(), Callback<Void> {

    lateinit var employeeApi: JsonPlaceHolderApi

    override fun onFailure(call: Call<Void>, t: Throwable) {
        Toast.makeText(this, "HTTP Delete Request Failed", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<Void>, response: Response<Void>) {

        //checking if delete was succussful, not the same as onFailure
        if (response.isSuccessful) {
            Toast.makeText(this, "Successfully deleted", Toast.LENGTH_LONG).show()
            progress_bar.isVisible = false
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_LONG).show()
            progress_bar.isVisible = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)
        title = "HTTP DELETE"

        employeeApi = JsonPlaceHolderApi.Factory.create()
        deleteEmployee()
    }

    private fun deleteEmployee(){
        employeeApi.deleteEmployeeById("1").enqueue(this)
    }
}
