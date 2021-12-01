package com.tvmaze.davidferrandiz.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.tvmaze.davidferrandiz.R

class GenericFeedbackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val messageTitleTextView: TextView by lazy {
        findViewById(R.id.default_message_title_tv)
    }

    private val retryButton: MaterialButton by lazy {
        findViewById(R.id.default_message_retry_btn)
    }

    private lateinit var onRetryButtonListener: () -> Unit

    init {
        LayoutInflater.from(context).inflate(R.layout.compound_default_message, this, true)
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        retryButton.setOnClickListener {
            onRetryButtonListener.invoke()
        }
    }

    fun populate(
        message: String,
        onRetryButtonListener: () -> Unit,
        buttonText: String = resources.getString(R.string.retry_button)
    ) {
        messageTitleTextView.text = message
        retryButton.text = buttonText
        this.onRetryButtonListener = onRetryButtonListener
    }
}