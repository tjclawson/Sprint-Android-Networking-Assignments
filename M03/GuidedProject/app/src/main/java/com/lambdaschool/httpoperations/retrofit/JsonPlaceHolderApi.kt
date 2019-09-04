package com.lambdaschool.httpoperations.retrofit

import com.google.gson.Gson
import com.lambdaschool.httpoperations.model.Employee
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface JsonPlaceHolderApi {
    // TODO: Create API for different endpoints

    //returns list of employee objects
    @GET("employees")
    fun getEmployees(): Call<List<Employee>>

    //returns object or list of objects with specified ID
    @GET("employees/{id}")
    fun getEmployeesById(@Path("id") employeeId: String): Call<List<Employee>>

    //returns object or list of objects with spedified age
    @GET("employees")
    fun getEmployeesByAge(@Query("age") employeeAge: String): Call<List<Employee>>

    @POST("employees")
    fun addNewEmployee(@Body employee: Employee): Call<Employee>

    @PUT("employee")
    fun updateEmployee(@Body employee: Employee): Call<Employee>

    @DELETE("employees/{id}")
    fun deleteEmployeeById(@Path("id") id: String): Call<Void>

    class Factory {
        //Can't define this in interface, so we put in class inside of interface
        companion object {
            val BASE_URL = "https://demo8143297.mockable.io/employees"
            val gson = Gson()
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .retryOnConnectionFailure(false)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            fun create(): JsonPlaceHolderApi {

            return Retrofit.Builder()
                .baseUrl(JsonPlaceHolderApi.Factory.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(JsonPlaceHolderApi::class.java)
        }
        }
    }
}