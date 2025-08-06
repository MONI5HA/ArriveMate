package com.example.arivemate.retrofit.about

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

object AboutRetrofitInstance {
    private val retrofitCountry by lazy {
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitWiki by lazy {
        Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val countryApi: CountryApiService by lazy {
        retrofitCountry.create(CountryApiService::class.java)
    }

    val wikiApi: WikiApiService by lazy {
        retrofitWiki.create(WikiApiService::class.java)
    }
}
