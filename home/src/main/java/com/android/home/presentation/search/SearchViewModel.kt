package com.android.home.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.core.platform.SingleLiveEvent
import com.android.domain.model.Content
import com.android.domain.response.ErrorType
import com.android.domain.response.Result
import com.android.domain.usecase.SearchMoviesUseCase
import com.android.home.R
import kotlinx.coroutines.launch

class SearchViewModel(
    private val context: Application,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : AndroidViewModel(context) {

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean>
        get() = _progressBarLiveData

    private val _errorLiveData = SingleLiveEvent<String>()
    val errorLiveData: SingleLiveEvent<String>
        get() = _errorLiveData

    private val _movieListLiveData = MutableLiveData<Pair<String, List<Content>>>()
    val movieListLiveData: MutableLiveData<Pair<String, List<Content>>>
        get() = _movieListLiveData

    fun search(query: String) {

        viewModelScope.launch {
            _progressBarLiveData.value = true
            when (val result = searchMoviesUseCase.searchMovies(query)) {
                is Result.Success -> {
                    _progressBarLiveData.value = false
                    if (result.data.isNullOrEmpty()) {
                        handleError(ErrorType.ContentUnavailable)
                    } else {
                        _movieListLiveData.value = Pair(query, result.data)
                    }
                }
                is Result.Error -> {
                    _progressBarLiveData.value = false
                    handleError(result.error)
                }
            }
        }
    }

    private fun handleError(error: ErrorType) {
        _errorLiveData.value = when (error) {
            ErrorType.ContentUnavailable -> context.resources.getString(R.string.no_results_found)
            ErrorType.FileError -> context.resources.getString(R.string.file_error)
            is ErrorType.GenericError -> context.resources.getString(R.string.generic_error)
        }
    }
}
