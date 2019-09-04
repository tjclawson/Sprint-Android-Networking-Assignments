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

class GetActivity : AppCompatActivity(), Callback<List<Employee>> {

    lateinit var employeeApi: JsonPlaceHolderApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)

        employeeApi = JsonPlaceHolderApi.Factory.create()

        val type = intent.getStringExtra("get")
        if (type == "simple") {
            title = "GET - Simple Request"
            getEmployees()
        } else if (type == "path") {
            title = "GET - Path Parameter: EmployeeId - 2"
            getEmployeesById("2")
        }
        else{
            title = "GET - Query Parameter: Age - 45"
            getEmployeesByAge("45")
        }
    }

    private fun getEmployees(){
        employeeApi.getEmployees().enqueue(this)
    }

    private fun getEmployeesById(employeeId: String){
        employeeApi.getEmployeesById(employeeId).enqueue(this)

    }

    private fun getEmployeesByAge(age: String){
        employeeApi.getEmployeesByAge(age).enqueue(this)
    }

    override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
        Toast.makeText(this@GetActivity, "Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>) {
        response.body()?.let {
            result.text = it.toString()
        }
        progress_bar.isVisible = false
    }
}
