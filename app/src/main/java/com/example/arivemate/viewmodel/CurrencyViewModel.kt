package com.example.arivemate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arivemate.data.model.CurrencyResponse
import com.example.arivemate.data.repository.CurrencyRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository()

    val rates = MutableLiveData<Map<String, Double>>()
    val error = MutableLiveData<String>()

    fun fetchRates(base: String) {
        repository.getRates(base).enqueue(object : Callback<CurrencyResponse> {
            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                if (response.isSuccessful) {
                    rates.value = response.body()?.conversion_rates
                } else {
                    error.value = "Failed to load rates"
                }
            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}
