package com.example.countriesapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.countriesapp.data.model.Country
import com.example.countriesapp.presentation.viewmodels.CountryViewModel

@Composable
fun CountryScreen(modifier: Modifier, viewModel: CountryViewModel) {
    viewModel.fetchAnswer()
    val countries by viewModel.countries.collectAsState()
    if (countries.isEmpty()) {
        Text(text = "Esta vaciaaa", modifier = Modifier.fillMaxSize())
    } else {
        CountryList(countries)
    }


}

@Composable
fun CountryList(countries: List<Country>) {
    LazyColumn {
        items(countries) { country -> CountryItem(country) }
    }
}

@Composable
fun CountryItem(country: Country) {

    Column {
        Spacer(modifier = Modifier.width(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
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
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        HorizontalDivider()
    }

}

