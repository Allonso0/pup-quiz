package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.model.QuizQuestion
import javax.inject.Inject

class GenerateQuiz @Inject constructor(
    private val getAllBreeds: GetAllBreeds,
    private val getRandomBreedImage: GetRandomBreedImage
){
    suspend operator fun invoke(questionCount: Int = 10) : List<QuizQuestion> {
        val allBreeds = getAllBreeds().shuffled()

        return (1..questionCount).map { questionIndex ->

            val correctBreed = allBreeds[questionIndex % allBreeds.size]
            val incorrectBreeds = allBreeds
                .filter { it.name != correctBreed.name }
                .shuffled()
                .take(3)
                .map { it.name }

            val options = (incorrectBreeds + correctBreed.name).shuffled()
            val imageURL = getRandomBreedImage(correctBreed.name)

            QuizQuestion(
                imageURL = imageURL,
                correctAnswer = correctBreed.name,
                options = options,
                breed = correctBreed
            )
        }
    }
}