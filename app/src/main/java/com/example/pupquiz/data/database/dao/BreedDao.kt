package com.example.pupquiz.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pupquiz.data.database.entity.BreedEntity

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds ORDER BY name")
    suspend fun getAll(): List<BreedEntity>

    @Query("SELECT * FROM breeds WHERE name = :name")
    suspend fun getByName(name: String): BreedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<BreedEntity>)

    @Query("DELETE FROM breeds WHERE name = :name")
    suspend fun delete(name: String)

    @Query("DELETE FROM breeds")
    suspend fun deleteAll()

    @Query("SELECT etag FROM breeds LIMIT 1")
    suspend fun getEtag(): String?

    @Query("SELECT lastModified FROM breeds LIMIT 1")
    suspend fun getLastModified(): String?
}