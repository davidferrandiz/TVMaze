package com.davidferrandiz.api.responses

import com.google.gson.annotations.SerializedName

data class ShowsResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("rating")
    var rating: ShowRatingResponse,
    @SerializedName("summary")
    var summary: String?,
    @SerializedName("image")
    var image: ShowImageResponse?,
    @SerializedName("officialSite")
    var officialSite: String?,
    @SerializedName("premiered")
    var premiered: String?
)

data class ShowImageResponse(
    @SerializedName("medium")
    var medium: String?,
    @SerializedName("original")
    var original: String?
)

data class ShowRatingResponse(
    @SerializedName("average")
    var average: Double?
)

