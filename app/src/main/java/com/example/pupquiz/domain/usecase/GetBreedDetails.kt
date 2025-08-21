package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.model.BreedDetails
import com.example.pupquiz.domain.repository.DogRepository
import javax.inject.Inject

class GetBreedDetails @Inject constructor(
    private val repository: DogRepository
) {

    suspend operator fun invoke(breedName: String): BreedDetails {
        val breed = repository.getAllBreeds().find { it.name == breedName }
            ?: throw IllegalArgumentException("Breed $breedName not found!")

        val imageURL = repository.getBreedImages(breedName, 1).firstOrNull() ?: ""

        return BreedDetails(
            breed = breed,
            imageURL = imageURL
        )
    }
}