package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.repository.DogRepository
import javax.inject.Inject

class CheckConnectivity @Inject constructor(
    private val repository: DogRepository
) {
    suspend operator fun invoke(): Boolean = repository.isOnline()
}