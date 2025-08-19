package com.example.pupquiz.data.repository

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.pupquiz.data.database.dao.BreedDao
import com.example.pupquiz.data.database.dao.BreedImageDao
import com.example.pupquiz.data.mapping.BreedImageMapper
import com.example.pupquiz.data.mapping.BreedMapper
import com.example.pupquiz.data.network.DogApiService
import com.example.pupquiz.data.network.dto.BreedDTO
import com.example.pupquiz.domain.model.Breed
import com.example.pupquiz.domain.repository.DogRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val api: DogApiService,
    private val breedDao: BreedDao,
    private val breedImageDao: BreedImageDao,
    private val breedMapper: BreedMapper,
    private val breedImageMapper: BreedImageMapper,
    @ApplicationContext private val context: Context
) : DogRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        return connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
    }

    override suspend fun saveBreedsToCache(breeds: List<Breed>) {
        val entities = breedMapper.mapToEntityList(breeds)
        breedDao.insertAll(entities)
    }

    override suspend fun loadBreedsFromCache(): List<Breed> {
        val entities = breedDao.getAll()
        return breedMapper.mapToDomainList(entities)
    }

    override suspend fun getAllBreeds(): List<Breed> {
        return try {
            if (isOnline()) {
                val response = api.getAllBreeds()
                val breeds = breedMapper.mapFromDto(response)

                saveBreedsToCache(breeds)
                breeds
            } else {
                loadBreedsFromCache()
            }
        } catch (e: Exception) {
            loadBreedsFromCache()
        }
    }

    override suspend fun getAllBreedsWithHeaders(
        etag: String?,
        lastModified: String?
    ): List<Breed> {
        return try {
            val response = api.getAllBreedsWithHeaders(etag, lastModified)

            if (response.code() == 304) {
                loadBreedsFromCache()
            } else {
                val breeds = breedMapper.mapFromDto(response.body()!!)
                val newEtag = response.headers()["Etag"]
                val newLastModified = response.headers()["Last-Modified"]

                val entities = breedMapper.mapToEntityList(breeds, newEtag, newLastModified)
                breedDao.insertAll(entities)

                breeds
            }
        } catch (e: Exception) {
            loadBreedsFromCache()
        }
    }

    override suspend fun getRandomBreedImage(breed: String): String {
        return try {
            if (isOnline()) {
                val response = api.getRandomBreedImage(breed)
                val imageUrl = response.message

                saveBreedImage(breed, imageUrl)
                imageUrl
            } else {
                getCachedBreedImage(breed) ?: getFallbackImageUrl(breed)
            }
        } catch (e: Exception) {
            getCachedBreedImage(breed) ?: getFallbackImageUrl(breed)
        }
    }

    override suspend fun getRandomSubBreedImage(breed: String, subBreed: String): String {
        val fullBreedName = "$breed-$subBreed"

        return try {
            if (isOnline()) {
                val response = api.getRandomSubBreedImage(breed, subBreed)
                val imageUrl = response.message

                saveBreedImage(fullBreedName, imageUrl)
                imageUrl
            } else {
                getCachedBreedImage(fullBreedName) ?: getFallbackImageUrl(fullBreedName)
            }
        } catch (e: Exception) {
            getCachedBreedImage(fullBreedName) ?: getFallbackImageUrl(fullBreedName)
        }
    }

    override suspend fun getBreedImages(breed: String, count: Int): List<String> {
        return try {
            if (isOnline()) {
                val images = mutableListOf<String>()

                coroutineScope {
                    val deferredImages = List(count) {
                        async {
                            try {
                                api.getRandomBreedImage(breed).message
                            } catch (e: Exception) {
                                null
                            }
                        }
                    }

                    deferredImages.forEach { deferred ->
                        deferred.await()?.let { imageUrl ->
                            images.add(imageUrl)
                            saveBreedImage(breed, imageUrl)
                        }
                    }
                }

                images.takeIf { it.isNotEmpty() } ?: getCachedBreedImages(breed, count)
            } else {
                getCachedBreedImages(breed, count)
            }
        } catch (e: Exception) {
            getCachedBreedImages(breed, count)
        }
    }

    override suspend fun checkForUpdates(): Boolean {
        return try {
            if (!isOnline()) {
                return false
            }

            val lastEtag = breedDao.getEtag()
            val lastModified = breedDao.getLastModified()

            val response = api.getAllBreedsWithHeaders(lastEtag, lastModified)

            when (response.code()) {
                304 -> false
                200 -> {
                    val breeds = breedMapper.mapFromDto(response.body()!!)
                    val newEtag = response.headers()["ETag"]
                    val newLastModified = response.headers()["Last-Modified"]

                    val entities = breedMapper.mapToEntityList(breeds, newEtag, newLastModified)
                    breedDao.insertAll(entities)

                    true
                }
                else -> false
            }
        } catch (e: Exception) {
            false
        }
    }

    // ==================== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ====================

    private suspend fun getCachedBreedImage(breed: String): String? {
        return breedImageDao.getRandomImageForBreed(breed)
    }

    private suspend fun getCachedBreedImages(breed: String, count: Int): List<String> {
        return breedImageDao.getImagesForBreed(breed, count)
            .map { it.imageURL }
            .takeIf { it.isNotEmpty() }
            ?: listOf(getFallbackImageUrl(breed))
    }

    private suspend fun saveBreedImage(breed: String, imageUrl: String) {
        try {
            val imageEntity = breedImageMapper.mapToEntity(breed, imageUrl)
            breedImageDao.insertAll(listOf(imageEntity))
        } catch (e: Exception) {
            Log.e("DogRepository", "Failed to save image for breed $breed: ${e.message}")
        }
    }

    private fun getFallbackImageUrl(breed: String): String {
        // Заглушка для случаев когда нет изображений в кэше
        return ""
    }
}