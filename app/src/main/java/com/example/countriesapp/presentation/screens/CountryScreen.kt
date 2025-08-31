package com.example.countriesapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    when {
        countries.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

        }

        else -> {
            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .background(color = MaterialTheme.colorScheme.background),
            ) {
                Text(
                    text = "Countries",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                CountryList(countries, navController)
            }
        }
    }
}

@Composable
fun CountryList(countries: List<Country>, navController: NavHostController) {
    LazyColumn {
        items(countries) { country -> CountryItem(country, navController) }
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

