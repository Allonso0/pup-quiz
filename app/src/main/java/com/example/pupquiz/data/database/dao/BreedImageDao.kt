package com.example.pupquiz.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pupquiz.data.database.entity.BreedImageEntity

@Dao
interface BreedImageDao {

    @Query("SELECT * FROM breed_images WHERE breedName = :breedName ORDER BY createdAt DESC LIMIT :count")
    suspend fun getImagesForBreed(breedName: String, count: Int): List<BreedImageEntity>

    @Query("SELECT imageURL FROM breed_images WHERE breedName = :breedName ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomImageForBreed(breedName: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<BreedImageEntity>)

    @Query("DELETE FROM breed_images WHERE breedName = :breedName")
    suspend fun deleteForBreed(breedName: String)

    @Query("DELETE FROM breed_images")
    suspend fun deleteAll()
}