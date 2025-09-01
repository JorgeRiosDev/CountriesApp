package com.example.countriesapp.presentation.navigation


sealed class NavRoute(val path: String) {
    data object Main : NavRoute("main")
    data object CountryDetails : NavRoute("countryDetails/{country_name}") {
        fun createRoute(countryName: String): String {
            return "countryDetails/$countryName"
        }
    }
}