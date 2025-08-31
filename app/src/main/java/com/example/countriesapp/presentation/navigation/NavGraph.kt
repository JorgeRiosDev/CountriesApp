package com.example.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.countriesapp.presentation.screens.CountryDetailsScreen
import com.example.countriesapp.presentation.screens.MainScreen
import com.example.countriesapp.presentation.viewmodels.CountryViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val countryViewModel: CountryViewModel = hiltViewModel<CountryViewModel>()

    NavHost(
        navController = navController, startDestination = NavRoute.Main.path
    ) {
        mainScreen(navController, this, countryViewModel)
        countryDetails(navController, this, countryViewModel)
    }
}

private fun mainScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
    countryViewModel: CountryViewModel
) {
    navGraphBuilder.composable(route = NavRoute.Main.path) {
        MainScreen(
            navController = navController, countryViewModel
        )
    }
}

private fun countryDetails(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
    countryViewModel: CountryViewModel
) {
    navGraphBuilder.composable(
        route = NavRoute.CountryDetails.path, arguments = listOf(
            navArgument("country_name") { type = NavType.StringType })
    ) { backStackEntry ->
        val countryName = backStackEntry.arguments?.getString("country_name")
        CountryDetailsScreen(
            countryName = countryName,
            countryViewModel
        )
    }
}
