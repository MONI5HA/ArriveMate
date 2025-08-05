package com.example.arivemate.data.model

data class NewsResponse(
    val results :List<News>?
)

data class News(
    val title:String?,
    val link:String?,
    val description:String?,
    val image_url:String?,
    val pubDate:String?

)
