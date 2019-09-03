package com.example.basic_android_networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<List<OceaniaCountry>> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_fetch_countries.setOnClickListener {
            OceaniaCountriesRetriever().getOceaniaCountries().enqueue(this)
        }
    }

    override fun onResponse(call: Call<List<OceaniaCountry>>, response: Response<List<OceaniaCountry>>) {
        if (response.isSuccessful) {
            val oceaniaCountryList = response.body()
            textview_countries.text = "" // use "oceaniaCountryList" to populate this TextView
        } else {
            val response = "response not successful; ${response.errorBody().toString()}"
            Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<List<OceaniaCountry>>, t: Throwable) {
        t.printStackTrace()
        val response = "faliure; ${t.printStackTrace()}"
        Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
    }
}
