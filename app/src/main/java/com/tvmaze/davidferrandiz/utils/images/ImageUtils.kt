package com.tvmaze.davidferrandiz.utils.images

import android.widget.ImageView
import androidx.constraintlayout.helper.widget.MotionPlaceholder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.setImageFromUrl(url: String, placeholder: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholder)
        .transition(DrawableTransitionOptions.withCrossFade(150))
        .into(this)
}

fun ImageView.setImageFromUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}