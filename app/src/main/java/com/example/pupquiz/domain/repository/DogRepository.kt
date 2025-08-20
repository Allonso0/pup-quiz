package com.example.pupquiz.domain.repository

import com.example.pupquiz.domain.model.Breed

interface DogRepository {
    suspend fun getAllBreeds(): List<Breed>
    suspend fun getAllBreedsWithHeaders(etag: String?, lastModified: String?): List<Breed>

    suspend fun getRandomBreedImage(breed: String): String
    suspend fun getRandomSubBreedImage(breed: String, subBreed: String): String
    suspend fun getBreedImages(breed: String, count: Int = 5): List<String>

    suspend fun checkForUpdates(): Boolean
    fun isOnline(): Boolean

    suspend fun saveBreedsToCache(breeds: List<Breed>)
    suspend fun loadBreedsFromCache(): List<Breed>
}