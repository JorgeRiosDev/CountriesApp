package com.example.countriesapp.data.repository

import com.example.countriesapp.data.model.Country
import com.example.countriesapp.data.remote.RestCountriesApi

class RestCountriesRepository(private val api: RestCountriesApi) {
    suspend fun fetchCountries(): List<Country> = api.getAllCountries()
}