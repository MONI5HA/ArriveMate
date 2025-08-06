package com.example.arivemate.data.model

import com.google.gson.JsonArray

data class CountryResponse(
    val name: Name,
    val capital: List<String>,
    val region: String,
    val subregion: String,
    val currencies: Map<String, Currency>,
    val languages: Map<String, String>,
    val latlng: List<Double>,
    val maps: Maps,
    val population: Long,
    val continents: List<String>,
    val flags: Flags
)

data class Name(
    val common: String,
    val official: String
)

data class Currency(
    val name: String,
    val symbol: String
)

data class Maps(
    val googleMaps: String
)

data class Flags(
    val png: String,
    val alt: String?
)

