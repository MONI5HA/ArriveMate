package com.example.arivemate.retrofit.about

import com.example.arivemate.data.model.WikiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiService {
    @GET("w/api.php")
    suspend fun getCountryHistory(
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "extracts",
        @Query("explaintext") explainText: Boolean = true,
        @Query("titles") title: String,
        @Query("format") format: String = "json"
    ): WikiResponse
}
