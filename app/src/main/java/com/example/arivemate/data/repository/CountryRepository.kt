package com.example.arivemate.data.repository

import com.example.arivemate.data.model.CountryResponse
import com.example.arivemate.data.model.WikiResponse
import com.example.arivemate.retrofit.about.AboutRetrofitInstance
import com.example.arivemate.retrofit.about.CountryApiService
import com.example.arivemate.retrofit.about.WikiApiService

class CountryRepository {
    suspend fun getCountryDetails(name: String): List<CountryResponse> {
        return AboutRetrofitInstance.countryApi.getCountryData(name)
    }

    suspend fun getCountryHistory(title: String): WikiResponse {
        return AboutRetrofitInstance.wikiApi.getCountryHistory(title = title)
    }
}
