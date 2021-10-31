package com.android.home.presentation.shared

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.android.core.constants.Constants
import com.android.core.extensions.FULL_STOP
import com.android.core.platform.AspectRatioHandler
import com.android.home.R
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesImageViewHandlerImplementor : MoviesImageViewHandler {

    override fun setMovieImageWidthAndHeight(
        view: ImageView?,
        aspectRatioHandler: AspectRatioHandler
    ) {
        with(aspectRatioHandler) {
            view?.let { imageView ->
                val (requiredWidth, requiredHeight) = getDimensionsThreeToFour(
                    imageView.imageMovie.context,
                    totalLandScapeItems = com.android.home.constants.HomeConstants.MOVIES_GRID_COUNT_LANDSCAPE,
                    totalPortraitItems = com.android.home.constants.HomeConstants.MOVIES_GRID_COUNT_PORTRAIT
                )
                setViewSize(imageView.imageMovie, requiredWidth, requiredHeight)
                setViewSize(imageView.imageMovie, requiredWidth, requiredHeight)
            }
        }
    }

    override fun setImage(imageView: ImageView?, imageName: String) {
        imageView?.imageMovie?.run {
            try {
                val resourceId = resources.getIdentifier(
                    imageName.split(String.FULL_STOP).firstOrNull(),
                    Constants.RESOURCE_TYPE_DRAWABLE,
                    context.packageName
                )
                setImageDrawable(ContextCompat.getDrawable(context, resourceId))
            } catch (e: Exception) {
                setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder))
            }
        }
    }
}
