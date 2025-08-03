package com.example.arivemate.retrofit.currency

import com.example.arivemate.data.model.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path



interface CurrencyApi {
    @GET("v6/1c9971e61fce82af2122939c/latest/{base}")
    fun getRates(@Path("base") base: String): Call<CurrencyResponse>
}
