package com.example.pupquiz.data.mapping

import com.example.pupquiz.data.database.entity.BreedImageEntity

class BreedImageMapper {

    fun mapToEntity(breedName: String, imageURL: String, isCached: Boolean = true): BreedImageEntity {
        return BreedImageEntity(
            id = "${breedName}_${System.currentTimeMillis()}",
            breedName = breedName,
            imageURL = imageURL,
            isCached = isCached,
            createdAt = System.currentTimeMillis()
        )
    }

    fun mapToEntityList(breedName: String, imageURLs: List<String>): List<BreedImageEntity> {
        return imageURLs.map { mapToEntity(breedName, it) }
    }
}