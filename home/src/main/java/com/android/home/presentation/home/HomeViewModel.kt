package com.android.home.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.android.domain.model.Content
import com.android.home.presentation.datasource.MoviesPagingSource

class HomeViewModel(private val moviesPagingSource: MoviesPagingSource) : ViewModel() {

    private val _movieListLiveData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        moviesPagingSource
    }.liveData
        .cachedIn(viewModelScope)
    val movieListLiveData: LiveData<PagingData<Content>>
        get() = _movieListLiveData

    companion object {
        private const val PAGE_SIZE = 20
    }
}
