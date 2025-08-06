package com.example.arivemate.retrofit.about

import com.example.arivemate.data.model.CountryResponse
import com.example.arivemate.data.model.WikiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryApiService {
    @GET("v3.1/name/{country}")
    suspend fun getCountryData(
        @Path("country") countryName: String,
        @Query("fullText") fullText: Boolean = true
    ): List<CountryResponse>
}
