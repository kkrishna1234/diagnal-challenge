package com.android.home.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.domain.model.Content
import com.android.home.R

class SearchRecyclerViewAdapter(
    private val context: Context,
    private val query: String,
    private val items: List<Content>
) : RecyclerView.Adapter<SearchRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecyclerViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return SearchRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchRecyclerViewHolder, position: Int) {
        holder.bindData(query, items[position])
    }

    override fun getItemCount() = items.size
}

