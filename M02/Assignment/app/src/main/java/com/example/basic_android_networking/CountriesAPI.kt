package com.example.basic_android_networking

import retrofit2.Call
import retrofit2.http.GET

interface CountriesAPI {
    @GET("oceania")
    fun getCountries(): Call<List<OceaniaCountry>>
}