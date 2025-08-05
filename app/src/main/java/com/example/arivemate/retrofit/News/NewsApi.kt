package com.example.arivemate.retrofit.News

import com.example.arivemate.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("latest")
    fun getNewsBycountry(
        @Query("apikey")
        apikey:String,
        @Query("country")
        country:String
    ):Call<NewsResponse>
}
