package com.davidferrandiz.presentation

import com.davidferrandiz.domain.entities.Show
import com.davidferrandiz.presentation.uidata.ShowDetailUI
import com.davidferrandiz.presentation.uidata.ShowListItem

fun Show.toUI() = ShowListItem(id, name, image?.medium)
fun Show.toDetailUI() = ShowDetailUI(name, image?.original, rating, summary, officialSite, premiered)