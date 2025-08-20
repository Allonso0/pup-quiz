package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.model.Breed
import com.example.pupquiz.domain.repository.DogRepository
import javax.inject.Inject

class GetAllBreeds @Inject constructor(
    private val repository: DogRepository
) {
    suspend operator fun invoke(): List<Breed> = repository.getAllBreeds()
}