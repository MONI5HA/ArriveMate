package com.example.arivemate.data.repository

import com.example.arivemate.retrofit.News.NewsRetrofitInstance

class NewsRepository {
    fun getNews(country:String,apikey:String)=
        NewsRetrofitInstance.api.getNewsBycountry(apikey,country)


}