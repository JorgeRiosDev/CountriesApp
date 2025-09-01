package com.example.countriesapp.data.model

data class Country(
    val flags: Flags,
    val name: Name,
    val languages: Map<String, String>?
)

data class Flags(
    val png: String,
    val svg: String,
    val alt: String
)

data class Name(
    val common: String,
    val official: String,
    val nativeName: Map<String, NativeName>
)

data class NativeName(
    val official: String,
    val common: String
)