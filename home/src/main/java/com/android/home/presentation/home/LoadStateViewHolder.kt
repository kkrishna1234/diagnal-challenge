package com.android.home.presentation.home

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.android.core.constants.Constants
import kotlinx.android.synthetic.main.item_progress_footer.view.*

class LoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindData(loadState: LoadState) {
        view.textLoadStateErrorMessage.isVisible = loadState !is LoadState.Loading
        view.progressLoadState.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error &&
            loadState.error.localizedMessage != Constants.Error.CONTENT_UNAVAILABLE
        ) {
            view.textLoadStateErrorMessage.text = loadState.error.localizedMessage
        }
    }

}
