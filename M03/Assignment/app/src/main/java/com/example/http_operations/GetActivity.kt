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
            title = "SIMPLE GET"
            getEmployees()
        } else if (type == "path") {
            title = "PATH GET"
            getEmployeesById("2")
        }
        else{
            title = "QUERY GET"
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
            for(i in 0 until it.size) {
                val name = it[i].name.toString()
                val id = it[i].id.toString()
                val age = it[i].age.toString()
                val title = it[i].title.toString()
                val employeeString = "Employee: Name - $name, ID - $id, Age - $age, Title - $title"
                if(i == 0) {
                    result.text = employeeString
                } else {
                    result.append("\n$employeeString")
                }
            }
        }
        progress_bar.isVisible = false
    }
}
