package com.davidferrandiz.domain.entities

import java.io.Serializable

data class Show(
    val id: Int,
    val name: String,
    val summary: String?,
    val rating: Double?,
    val image: ShowImage?,
    val officialSite: String?,
    val premiered: String?
) : Serializable

data class ShowImage(
    val medium: String?,
    val original: String?,
)