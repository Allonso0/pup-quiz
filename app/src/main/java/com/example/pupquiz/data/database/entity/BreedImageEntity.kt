package com.example.pupquiz.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed_images")
data class BreedImageEntity(
    @PrimaryKey val id: String,
    val breedName: String,
    val imageURL: String,
    val isCached: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
