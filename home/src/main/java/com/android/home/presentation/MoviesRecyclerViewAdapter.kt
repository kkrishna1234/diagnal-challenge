package com.android.home.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.android.domain.model.Content
import com.android.home.R

class MoviesRecyclerViewAdapter(
    private val context: Context
) : PagingDataAdapter<Content, MoviesRecyclerViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesRecyclerViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MoviesRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesRecyclerViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            VIEW_TYPE_MOVIES
        } else {
            VIEW_TYPE_LOADING
        }
    }

    companion object {
        const val VIEW_TYPE_MOVIES = 1
        const val VIEW_TYPE_LOADING = 2
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem == newItem
    }
}
