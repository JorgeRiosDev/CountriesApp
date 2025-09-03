package com.example.countriesapp.presentation.viewmodels

import CountryState
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

    private val _countriesByName = MutableStateFlow<List<Country>>(emptyList())
    val countriesByName: StateFlow<List<Country>> = _countriesByName

    private val _countryState = MutableStateFlow(CountryState())
    val countryState: StateFlow<CountryState> = _countryState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        fetchAnswer()
    }

    fun toggleSearching() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            _searchQuery.value = ""
            _countriesByName.value = emptyList()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
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
                _countryState.value = CountryState(
                    country = result.firstOrNull() ?: throw Exception("Country not found")
                )
            } catch (e: Exception) {
                _countryState.value = CountryState(error = e.message)
            }
        }
    }

    fun fetchCountriesByName() {
        viewModelScope.launch {
            try {
                val result = repo.fetchCountriesByName(_searchQuery.value.trim().lowercase())
                _countriesByName.value = result
            } catch (e: Exception) {
                _countriesByName.value = emptyList()
            }
        }
    }
}