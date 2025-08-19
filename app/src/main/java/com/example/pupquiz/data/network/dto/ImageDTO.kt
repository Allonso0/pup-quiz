package com.example.pupquiz.data.network.dto

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: String
)
