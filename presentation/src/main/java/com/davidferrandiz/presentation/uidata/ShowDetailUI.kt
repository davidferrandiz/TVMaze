package com.davidferrandiz.presentation.uidata

import java.io.Serializable

data class ShowDetailUI(
    val name : String,
    val image: String?,
    val rating: Double?,
    val summary: String?,
    val officialSite: String?,
    val premiered: String?
) : Serializable