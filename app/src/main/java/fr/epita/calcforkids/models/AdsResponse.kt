package fr.epita.calcforkids.models


import com.google.gson.annotations.SerializedName

data class AdsResponse(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("content")
    val content: String
)