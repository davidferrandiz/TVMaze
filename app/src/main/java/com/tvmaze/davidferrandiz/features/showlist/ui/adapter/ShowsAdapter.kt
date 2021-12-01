package com.tvmaze.davidferrandiz.features.showlist.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidferrandiz.presentation.uidata.ShowListItem
import com.tvmaze.davidferrandiz.R
import com.tvmaze.davidferrandiz.databinding.ShowRowBinding
import com.tvmaze.davidferrandiz.utils.images.setImageFromUrl

class ShowsAdapter(private val listener: (Int) -> Unit) :
    PagingDataAdapter<ShowListItem, ShowsAdapter.AdapterViewHolder>(DataDifferentiator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.show_row, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val show = getItem(position)
        show?.let {
            holder.bind(it)
            holder.itemView.setOnClickListener { listener(show.id) }
            setItemHighlightedWhenPressed(holder.itemView)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setItemHighlightedWhenPressed(itemView: View) {
        itemView.setOnTouchListener { view, event ->

            val image: ConstraintLayout = view as ConstraintLayout

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    image.alpha = 0.75f
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    image.alpha = 1f
                }
            }

            false
        }
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ShowRowBinding.bind(view)

        fun bind(show: ShowListItem) = with(binding) {
            showsNameTv.text = show.name
            show.image?.let {
                showsImageIv.setImageFromUrl(it, R.drawable.show_placeholder)
            }
        }
    }

    object DataDifferentiator : DiffUtil.ItemCallback<ShowListItem>() {

        override fun areItemsTheSame(oldItem: ShowListItem, newItem: ShowListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShowListItem, newItem: ShowListItem): Boolean {
            return oldItem == newItem
        }
    }
}