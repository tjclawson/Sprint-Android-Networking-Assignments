package com.example.http_operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.http_operations.model.Employee
import kotlinx.android.synthetic.main.activity_get.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PutActivity : AppCompatActivity(), Callback<Employee> {

    lateinit var employeeApi: JsonPlaceHolderApi

    override fun onFailure(call: Call<Employee>, t: Throwable) {
        Toast.makeText(this, "HTTP Put Request Failed", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
        response.body()?.let {

            val name = it.name.toString()
            val id = it.id.toString()
            val age = it.age.toString()
            val title = it.title.toString()
            val employeeString = "Employee Updated:\nName - $name\nID - $id\nAge - $age\nTitle - $title"
            result.text = employeeString
        }
        progress_bar.isVisible = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)
        title = "HTTP PUT"

        employeeApi = JsonPlaceHolderApi.Factory.create()
        updateEmployee()
    }

    private fun updateEmployee() {
        val employee = Employee("Steve", 1, 25, "Principal Engineer")
        employeeApi.updateEmployee(employee).enqueue(this)
    }
}
