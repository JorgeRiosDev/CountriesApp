package com.example.countriesapp.presentation.viewmodels

import CountryState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.data.model.Country
import com.example.countriesapp.data.repository.RestCountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repo: RestCountriesRepository
) : ViewModel() {


    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries

    private val _countryState = MutableStateFlow(CountryState())
    val countryState: StateFlow<CountryState> = _countryState.asStateFlow()

    init {
        fetchAnswer()
    }

    fun fetchAnswer() {
        viewModelScope.launch {
            try {
                val result = repo.fetchCountries()
                _countries.value = result
            } catch (e: Exception) {
                _countries.value = emptyList()
            }
        }
    }

    fun fetchCountryByName(name: String) {
        viewModelScope.launch {
            _countryState.value = CountryState(isLoading = true)
            try {
                val result = repo.fetchCountryByName(name)
                _countryState.value = CountryState(country = result.firstOrNull() ?: throw Exception("Country not found"))
                Log.e("a", result.toString())
            } catch (e: Exception) {
                _countryState.value = CountryState(error = e.message)
                Log.e("a", e.toString())
            }
        }
    }
}