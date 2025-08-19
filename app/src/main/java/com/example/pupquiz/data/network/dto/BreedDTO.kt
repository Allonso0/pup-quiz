package com.example.pupquiz.data.network.dto

import com.google.gson.annotations.SerializedName

data class BreedDTO(
    @SerializedName("message")
    val message: Map<String, List<String>>,

    @SerializedName("status")
    val status: String
)