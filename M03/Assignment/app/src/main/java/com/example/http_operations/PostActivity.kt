package com.example.http_operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.http_operations.model.Employee
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_get.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity(), Callback<Employee> {

    override fun onFailure(call: Call<Employee>, t: Throwable) {
        Toast.makeText(this, "HTTP Post Request Failed", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
        response.body()?.let {
            val name = it.name.toString()
            val id = it.id.toString()
            val age = it.age.toString()
            val title = it.title.toString()
            val employeeString = "Employee Added:\nName - $name\nID - $id\nAge - $age\nTitle - $title"
            result.text = employeeString
        }
        progress_bar.isVisible = false
    }

    lateinit var employeeApi: JsonPlaceHolderApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)
        title = "HTTP POST"

        employeeApi = JsonPlaceHolderApi.Factory.create()
        addNewEmployee()
    }

    private fun addNewEmployee(){
        val employee = Employee("David", 7, 30, "Intern")
        employeeApi.addNewEmployee(employee).enqueue(this)
    }
}
