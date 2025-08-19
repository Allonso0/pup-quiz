package com.example.pupquiz.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey val name: String,
    val subBreeds: List<String>,
    val imageURL: String,
    val lastUpdated: Long,
    val etag: String? = null,
    val lastModified: String? = null
)
