import com.example.countriesapp.data.model.CountryDetails

data class CountryState(
    val isLoading: Boolean = false,
    val country: CountryDetails? = null,
    val error: String? = null
)