package com.example.arivemate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arivemate.data.model.News
import com.example.arivemate.data.model.NewsResponse
import com.example.arivemate.data.repository.NewsRepository
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class NewsViewModel :ViewModel() {
    private val repository=NewsRepository()
    val article =MutableLiveData<List<News>>()
    val error = MutableLiveData<String>()

    fun fetchNews(country:String,apikey:String){
        repository.getNews(country,apikey).enqueue(object :Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    article.value = response.body()?.results ?: emptyList()
                } else {
                    error.value = "Failed to load news: ${response.code()} - ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<NewsResponse>,t:Throwable){
                error.value= t.message
                
            }
        })
    }


}