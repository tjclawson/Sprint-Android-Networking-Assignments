package com.example.http_operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.http_operations.model.Employee
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity(), Callback<Employee> {

    override fun onFailure(call: Call<Employee>, t: Throwable) {
        Toast.makeText(this, "HTTP Post Request Failed", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
        response.body()?.let {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    lateinit var employeeApi: JsonPlaceHolderApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        employeeApi = JsonPlaceHolderApi.Factory.create()
        addNewEmployee()
    }

    private fun addNewEmployee(){
        val employee = Employee("David", 7, 30, "Intern")
        employeeApi.addNewEmployee(employee).enqueue(this)
    }
}
