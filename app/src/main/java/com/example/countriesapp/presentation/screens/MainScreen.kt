package com.example.countriesapp.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.countriesapp.presentation.viewmodels.CountryViewModel

@Composable
fun MainScreen(navController: NavHostController, viewModel: CountryViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        ) { innerPadding ->
        CountryScreen(navController, modifier = Modifier.padding(innerPadding), viewModel)
    }
}