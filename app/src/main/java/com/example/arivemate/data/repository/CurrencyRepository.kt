package com.example.arivemate.data.repository

import com.example.arivemate.retrofit.currency.RetrofitInstance

class CurrencyRepository {
    fun getRates(base: String) = RetrofitInstance.api.getRates(base)
}
