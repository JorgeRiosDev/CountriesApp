package com.example.countriesapp.data.remote

import com.example.countriesapp.data.model.Country
import retrofit2.http.GET
import retrofit2.http.Query

interface RestCountriesApi {
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String = "name,flags"
    ): List<Country>
}