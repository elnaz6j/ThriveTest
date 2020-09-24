package com.example.thrivetest.data

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("id") val id: Long,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String
)