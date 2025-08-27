package com.example.countriesapp.di

import com.example.countriesapp.data.remote.RestCountriesApi
import com.example.countriesapp.data.repository.RestCountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideYesNoApi(retrofit: Retrofit): RestCountriesApi {
        return retrofit.create(RestCountriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideYesNoRepository(api: RestCountriesApi): RestCountriesRepository {
        return RestCountriesRepository(api)
    }
}