package com.android.home.search

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.domain.model.Content
import com.android.domain.response.ErrorType
import com.android.domain.response.Result
import com.android.domain.usecase.SearchMoviesUseCase
import com.android.home.MainCoroutineRule
import com.android.home.R
import com.android.home.presentation.search.SearchViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var cut: SearchViewModel

    @RelaxedMockK
    private lateinit var context: Application

    @RelaxedMockK
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @RelaxedMockK
    private lateinit var progressBarObserver: Observer<Boolean>

    @RelaxedMockK
    private lateinit var moviesListObserver: Observer<Pair<String, List<Content>>>

    @RelaxedMockK
    private lateinit var errorObserver: Observer<String>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        cut = SearchViewModel(context = context, searchMoviesUseCase = searchMoviesUseCase).apply {
            progressBarLiveData.observeForever(progressBarObserver)
            movieListLiveData.observeForever(moviesListObserver)
            errorLiveData.observeForever(errorObserver)
        }
    }

    @Test
    fun `Given search result fetch in progress When search method invoked Then progress observer notified as true`() {

        // When
        cut.search("test")

        // Then
        verify { progressBarObserver.onChanged(true) }
    }

    @Test
    fun `Given there are results When search method invoked Then moviesListObserver observer notified`() {

        // Given
        val query = "test"
        val searchResults = mockk<List<Content>>(relaxed = true)
        coEvery { searchMoviesUseCase.searchMovies(query) } returns Result.Success(searchResults)

        // When
        cut.search(query)

        // Then
        val capturedResult = CapturingSlot<Pair<String, List<Content>>>()
        verifyOrder {
            progressBarObserver.onChanged(false)
            moviesListObserver.onChanged(capture(capturedResult))
        }
        Assert.assertEquals(query, capturedResult.captured.first)
        Assert.assertEquals(searchResults, capturedResult.captured.second)
    }

    @Test
    fun `Given file error while fetching search results When search method invoked Then error observer notified`() {

        // Given
        val query = "test"
        coEvery { searchMoviesUseCase.searchMovies(query) } returns Result.Error(ErrorType.FileError)

        // When
        cut.search(query)

        // Then
        val capturedResult = CapturingSlot<String>()
        verifyOrder {
            progressBarObserver.onChanged(false)
            errorObserver.onChanged(capture(capturedResult))
        }
        Assert.assertEquals(context.getString(R.string.file_error), capturedResult.captured)
    }

    @Test
    fun `Given there are no results When search method invoked Then error observer notified`() {

        // Given
        coEvery { searchMoviesUseCase.searchMovies(any()) } returns Result.Success(emptyList())

        // When
        cut.search("test")

        // Then
        val capturedResult = CapturingSlot<String>()
        verifyOrder {
            progressBarObserver.onChanged(false)
            errorObserver.onChanged(capture(capturedResult))
        }
        Assert.assertEquals(context.getString(R.string.no_results_found), capturedResult.captured)
    }

    @Test
    fun `Given generic error When search method invoked Then error observer notified`() {

        // Given
        coEvery { searchMoviesUseCase.searchMovies(any()) } returns Result.Error(
            ErrorType.GenericError(Throwable())
        )

        // When
        cut.search("test")

        // Then
        val capturedResult = CapturingSlot<String>()
        verifyOrder {
            progressBarObserver.onChanged(false)
            errorObserver.onChanged(capture(capturedResult))
        }
        Assert.assertEquals(context.getString(R.string.generic_error), capturedResult.captured)
    }
}
