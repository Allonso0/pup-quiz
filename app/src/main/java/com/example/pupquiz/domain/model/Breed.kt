package com.example.pupquiz.domain.model

data class Breed(
    val name: String,
    val subBreeds: List<String>,
    val imageURL: String = "",
    val lastUpdated: Long = System.currentTimeMillis()
)
