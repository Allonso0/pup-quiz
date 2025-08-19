package com.example.pupquiz.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pupquiz.data.database.dao.BreedDao
import com.example.pupquiz.data.database.dao.BreedImageDao
import com.example.pupquiz.data.database.entity.BreedEntity
import com.example.pupquiz.data.database.entity.BreedImageEntity

@Database(
    entities = [
        BreedEntity::class,
        BreedImageEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class DogDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao

    abstract fun breedImageDao(): BreedImageDao
}