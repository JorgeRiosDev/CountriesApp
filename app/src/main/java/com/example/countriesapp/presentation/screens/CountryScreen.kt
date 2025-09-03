package com.example.countriesapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.countriesapp.data.model.Country
import com.example.countriesapp.presentation.navigation.NavRoute
import com.example.countriesapp.presentation.viewmodels.CountryViewModel

@Composable
fun CountryScreen(
    navController: NavHostController, modifier: Modifier, viewModel: CountryViewModel
) {

    val countries by viewModel.countries.collectAsState()
    val countriesByName by viewModel.countriesByName.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    when {
        countries.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        else -> {
            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .background(color = MaterialTheme.colorScheme.background),
            ) {
                CountriesTopBar(
                    isSearching = isSearching,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                    onToggleSearch = { viewModel.toggleSearching() },
                    onSearch = { viewModel.fetchCountriesByName() }
                )
                CountryList(if (isSearching) countriesByName else countries, navController)
            }
        }
    }
}

@Composable
fun CountryList(
    countries: List<Country>,
    navController: NavHostController
) {
    LazyColumn {
        items(countries) { country ->
            CountryItem(
                country,
                navController
            )
        }
    }
}

@Composable
fun CountryItem(country: Country, navController: NavHostController) {
    val languagesText = country.languages?.values?.joinToString(", ") ?: "No languages"

    Column {
        Spacer(modifier = Modifier.width(6.dp))
        Row(
            modifier = Modifier
                .clickable(onClick = {
                    navController.navigate(NavRoute.CountryDetails.createRoute(country.name.official))
                })
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = country.flags.png,
                contentDescription = country.flags.alt,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = country.name.common,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(end = 12.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = languagesText, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.width(6.dp))
        HorizontalDivider()
    }
}

@Composable
fun CountriesTopBar(
    isSearching: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onSearch: () -> Unit
) {
    if (isSearching) {
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(true) { focusRequester.requestFocus() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                singleLine = true,
                placeholder = { Text("Search country...") },
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch() }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),

                )
            IconButton(onClick = onSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = onToggleSearch) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close search")
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Countries",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = onToggleSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Open search")
            }
        }
    }
}


