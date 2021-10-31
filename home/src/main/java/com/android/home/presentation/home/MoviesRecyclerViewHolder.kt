package com.android.home.presentation.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.core.platform.AspectRatioHandler
import com.android.domain.model.Content
import com.android.home.presentation.shared.MoviesImageViewHandler
import com.android.home.presentation.shared.MoviesImageViewHandlerImplementor
import kotlinx.android.synthetic.main.item_movie.view.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MoviesRecyclerViewHolder(
    itemView: View,
    private val moviesImageViewHandler: MoviesImageViewHandler = MoviesImageViewHandlerImplementor()
) : RecyclerView.ViewHolder(itemView),
    MoviesImageViewHandler by moviesImageViewHandler,
    KoinComponent {
    private val aspectRatioHandler by inject<AspectRatioHandler>()

    init {
        moviesImageViewHandler.setMovieImageWidthAndHeight(itemView.imageMovie, aspectRatioHandler)
    }

    fun bindData(data: Content) {
        itemView.textMovieTitle?.text = data.name
        moviesImageViewHandler.setImage(itemView.imageMovie, data.posterImage)
    }
}
