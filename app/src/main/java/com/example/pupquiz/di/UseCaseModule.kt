package com.example.pupquiz.di

import com.example.pupquiz.domain.repository.DogRepository
import com.example.pupquiz.domain.usecase.GenerateQuiz
import com.example.pupquiz.domain.usecase.GetAllBreeds
import com.example.pupquiz.domain.usecase.GetRandomBreedImage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGenerateQuiz(
        getAllBreeds: GetAllBreeds,
        getRandomBreedImage: GetRandomBreedImage
    ): GenerateQuiz {
        return GenerateQuiz(getAllBreeds, getRandomBreedImage)
    }

    @Provides
    fun provideGetAllBreeds(repository: DogRepository): GetAllBreeds {
        return GetAllBreeds(repository)
    }

    @Provides
    fun provideGetRandomBreedImage(repository: DogRepository): GetRandomBreedImage {
        return GetRandomBreedImage(repository)
    }
}