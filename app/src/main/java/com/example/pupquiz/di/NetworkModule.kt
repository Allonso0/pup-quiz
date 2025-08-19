package com.example.pupquiz.di

import android.content.Context
import com.example.pupquiz.data.database.dao.BreedDao
import com.example.pupquiz.data.database.dao.BreedImageDao
import com.example.pupquiz.data.mapping.BreedImageMapper
import com.example.pupquiz.data.mapping.BreedMapper
import com.example.pupquiz.data.network.DogApiService
import com.example.pupquiz.data.network.RetrofitClient
import com.example.pupquiz.data.repository.DogRepositoryImpl
import com.example.pupquiz.domain.repository.DogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDogApiService(): DogApiService {
        return RetrofitClient.api
    }

    @Provides
    @Singleton
    fun provideDogRepository(
        api: DogApiService,
        breedDao: BreedDao,
        breedImageDao: BreedImageDao,
        breedMapper: BreedMapper,
        breedImageMapper: BreedImageMapper,
        @ApplicationContext context: Context
    ) : DogRepository {
        return DogRepositoryImpl(
            api = api,
            breedDao = breedDao,
            breedImageDao = breedImageDao,
            breedMapper = breedMapper,
            breedImageMapper = breedImageMapper,
            context = context
        )
    }
}