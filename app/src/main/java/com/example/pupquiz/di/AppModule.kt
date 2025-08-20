package com.example.pupquiz.di

import android.content.Context
import androidx.room.Room
import com.example.pupquiz.data.database.DogDatabase
import com.example.pupquiz.data.database.dao.BreedDao
import com.example.pupquiz.data.database.dao.BreedImageDao
import com.example.pupquiz.data.mapping.BreedImageMapper
import com.example.pupquiz.data.mapping.BreedMapper
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.data.repository.SettingsRepositoryImpl
import com.example.pupquiz.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesPreferencesManager(@ApplicationContext context: Context) : PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun providesSettingsRepository(prefs: PreferencesManager) : SettingsRepository {
        return SettingsRepositoryImpl(prefs)
    }

    @Provides
    @Singleton
    fun provideDogDatabase(@ApplicationContext context: Context): DogDatabase {
        return Room.databaseBuilder(
            context,
            DogDatabase::class.java,
            "dog_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBreedMapper(): BreedMapper {
        return BreedMapper()
    }

    @Provides
    @Singleton
    fun provideBreedImageMapper(): BreedImageMapper {
        return BreedImageMapper()
    }

    @Provides
    @Singleton
    fun provideBreedDao(database: DogDatabase): BreedDao {
        return database.breedDao()
    }

    @Provides
    @Singleton
    fun provideBreedImageDao(database: DogDatabase): BreedImageDao {
        return database.breedImageDao()
    }
}