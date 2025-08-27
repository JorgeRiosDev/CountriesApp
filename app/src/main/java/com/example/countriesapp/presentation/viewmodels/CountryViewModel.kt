package com.example.countriesapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun fetchAnswer() {
        viewModelScope.launch {
            try {
                val result = repo.fetchCountries()
                _countries.value = result
                Log.d("CountryViewModel", "Response size: ${result.size}")
                result.forEach { Log.d("CountryViewModel", "Country: ${it.name.common}") }
            } catch (e: Exception) {
                Log.e("CountryViewModel", "Error fetching countries", e)
                _countries.value = emptyList()
            }
        }
    }
}