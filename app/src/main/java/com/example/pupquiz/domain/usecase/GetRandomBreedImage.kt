package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.repository.DogRepository
import javax.inject.Inject

class GetRandomBreedImage @Inject constructor(
    private val repository: DogRepository
) {
    suspend operator fun invoke(breed: String): String {
        return repository.getRandomBreedImage(breed)
    }
}