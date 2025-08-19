package com.example.pupquiz.data.network

import com.example.pupquiz.data.network.dto.BreedDTO
import com.example.pupquiz.data.network.dto.ImageDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DogApiService {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedDTO

    @GET("breeds/list/all")
    suspend fun getAllBreedsWithHeaders(
        @Header("If-None-Match") etag: String? = null,
        @Header("If-Modified-Since") lastModified: String? = null,
    ) : Response<BreedDTO>

    @GET("breed/{breed}/images/random")
    suspend fun getRandomBreedImage(
        @Path("breed") breed: String
    ) : ImageDTO

    @GET("breed/{breed}/{sub-breed}/images/random")
    suspend fun getRandomSubBreedImage(
        @Path("breed") breed: String,
        @Path("sub-breed") subBreed: String
    ) : ImageDTO
}