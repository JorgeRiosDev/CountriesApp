package com.example.countriesapp.data.remote

import com.example.countriesapp.data.model.Country
import com.example.countriesapp.data.model.CountryDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestCountriesApi {
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String = "name,flags,languages"
    ): List<Country>

    @GET("name/{country_name}")
    suspend fun getCountryByName(
        @Path("country_name") countryName: String
    ): List<CountryDetails>

    @GET("name/{country_name}")
    suspend fun getCountriesByName(
        @Path("country_name") countryName: String
    ): List<Country>

}