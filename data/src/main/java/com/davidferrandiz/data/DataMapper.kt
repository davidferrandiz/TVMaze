package com.davidferrandiz.data

import com.davidferrandiz.api.responses.ShowImageResponse
import com.davidferrandiz.api.responses.ShowsResponse
import com.davidferrandiz.domain.entities.Show
import com.davidferrandiz.domain.entities.ShowImage

fun ShowsResponse.toDomain() =
    with(this) {
        Show(id,
            name,
            summary,
            rating.average,
            image?.let { mapImage(it) },
            officialSite,
            premiered)
    }

private fun mapImage(image: ShowImageResponse) = with(image) { ShowImage(medium, original) }