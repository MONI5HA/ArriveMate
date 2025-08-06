package com.example.arivemate.data.model

data class WikiResponse(
    val query: WikiQuery
)

data class WikiQuery(
    val pages: Map<String, WikiPage>
)

data class WikiPage(
    val title: String,
    val extract: String
)
