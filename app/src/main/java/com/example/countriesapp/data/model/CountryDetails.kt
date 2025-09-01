package com.example.countriesapp.data.model

data class CountryDetails(
    val name: Name,
    val flag: String,
    val capital: List<String>?,
    val flags: Flags,
    val region: String,
    val subregion: String?,
    val population: Long,
    val languages: Map<String, String>?,
    val currencies: Map<String, Currency>?,
    val borders: List<String>?,
    val maps: Maps,
    val cca3: String
)

data class Currency(
    val name: String,
    val symbol: String?
)

data class Maps(
    val googleMaps: String,
    val openStreetMaps: String
)