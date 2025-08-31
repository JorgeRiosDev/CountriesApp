package com.example.countriesapp.presentation.screens

import CountryState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.countriesapp.presentation.viewmodels.CountryViewModel
import com.example.countriesapp.utils.CountryDetailsSkeleton


@Composable
fun CountryDetailsScreen(countryName: String?, viewModel: CountryViewModel) {

    LaunchedEffect(Unit) {
        viewModel.fetchCountryByName(countryName.toString())
    }

    val countryState by viewModel.countryState.collectAsState()


    Surface(color = MaterialTheme.colorScheme.background) {
        if (countryState.country == null) {
            CountryDetailsSkeleton()
        } else {
            CountryDetailsContent(countryState)
        }

    }
}

@Composable
fun CountryDetailsContent(countryState: CountryState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = countryState.country?.name?.official.toString(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        AsyncImage(
            model = countryState.country?.flags?.png,
            contentDescription = countryState.country?.name?.common,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.CenterHorizontally)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Capital", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = countryState.country?.capital?.firstOrNull() ?: "No capital",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Region", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = countryState.country?.region ?: "No region",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Subregion", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = countryState.country?.subregion ?: "No subregion",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Population", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = countryState.country?.population?.toString() ?: "0",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        countryState.country?.languages?.let { langs ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Languages", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = langs.values.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        countryState.country?.currencies?.let { currencies ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Currencies", style = MaterialTheme.typography.titleMedium)
                    currencies.values.forEach { currency ->
                        Text(
                            text = "${currency.name} (${currency.symbol ?: "-"})",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Borders", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = countryState.country?.borders?.joinToString(", ") ?: "No borders",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Flag Emoji", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = countryState.country?.flag ?: "No flag",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}