package com.example.http_operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.http_operations.model.Employee
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
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_put)

        employeeApi = JsonPlaceHolderApi.Factory.create()
        updateEmployee()
    }

    private fun updateEmployee() {
        val employee = Employee("Steve", 1, 25, "Principal Engineer")
        employeeApi.updateEmployee(employee).enqueue(this)
    }
}
