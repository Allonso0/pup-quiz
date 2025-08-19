package com.example.pupquiz.data.mapping

import com.example.pupquiz.data.database.entity.BreedEntity
import com.example.pupquiz.data.network.dto.BreedDTO
import com.example.pupquiz.domain.model.Breed

class BreedMapper {

    fun mapToEntity(breed: Breed, etag: String? = null, lastModified: String? = null): BreedEntity {
        return BreedEntity(
            name = breed.name,
            subBreeds = breed.subBreeds,
            imageURL = breed.imageURL,
            lastUpdated = System.currentTimeMillis(),
            etag = etag,
            lastModified = lastModified
        )
    }

    fun mapToDomain(entity: BreedEntity): Breed {
        return Breed(
            name = entity.name,
            subBreeds = entity.subBreeds,
            imageURL = entity.imageURL
        )
    }

    fun mapToEntityList(breeds: List<Breed>, etag: String? = null, lastModified: String? = null) : List<BreedEntity> {
        return breeds.map { mapToEntity(it, etag, lastModified) }
    }

    fun mapToDomainList(entities: List<BreedEntity>): List<Breed> {
        return entities.map { mapToDomain(it) }
    }

    fun mapFromDto(dto: BreedDTO): List<Breed> {
        return dto.message.map { (breed, subBreed) ->
            Breed(
                name = breed,
                subBreeds = subBreed,
                imageURL = ""
            )
        }
    }
}