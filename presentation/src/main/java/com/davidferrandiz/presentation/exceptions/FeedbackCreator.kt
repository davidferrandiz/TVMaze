package com.davidferrandiz.presentation.uidata.utils

import android.content.Context
import com.davidferrandiz.presentation.exceptions.ExceptionType
import com.davidferrandiz.presentation.R
import javax.inject.Inject

class FeedbackCreator @Inject constructor(private val context: Context) {

    fun create(feedbackType: FeedbackType): String {
        return when (feedbackType) {
            FeedbackType.GENERIC_EMPTY_VIEW -> context.getString(R.string.error_empty_data)
        }
    }

    fun create(ex: ExceptionType): String {
        return when (ex) {
            is ExceptionType.NetworkException -> context.getString(R.string.error_no_network)
            is ExceptionType.GenericException -> context.getString(R.string.error_generic)
            else -> context.getString(R.string.error_generic)
        }
    }
}

enum class FeedbackType {
    GENERIC_EMPTY_VIEW
}