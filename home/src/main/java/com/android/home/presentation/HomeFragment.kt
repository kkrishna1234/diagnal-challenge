package com.android.home.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.android.core.extensions.showToast
import com.android.core.platform.BaseFragment
import com.android.home.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment() {
    private val homeViewModel by viewModel<HomeViewModel>()
    private val moviesAdapter by lazy {
        context?.let { MoviesRecyclerViewAdapter(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observeLiveData()
    }

    private fun initUi() {
        recyclerViewHome?.run {
            val gridLayoutManager = GridLayoutManager(
                context,
                resources.getInteger(R.integer.movies_grid_column_count),
                GridLayoutManager.VERTICAL,
                false
            )
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = this@HomeFragment.moviesAdapter?.getItemViewType(position)
                    return if (viewType == MoviesRecyclerViewAdapter.VIEW_TYPE_LOADING) {
                        resources.getInteger(R.integer.loading_grid_column_count)
                    } else {
                        resources.getInteger(R.integer.movies_grid_column_count)
                    }
                }
            }
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = this@HomeFragment.moviesAdapter?.withLoadStateFooter(
                footer = MoviesLoadStateAdapter()
            )
        }
        setLoadStateListener()
    }

    private fun setLoadStateListener() {
        moviesAdapter?.addLoadStateListener { loadStates ->
            progressBar.isVisible = loadStates.refresh is LoadState.Loading
            if (loadStates.refresh is LoadState.Error) context.showToast(getString(R.string.generic_error))
        }
    }

    private fun observeLiveData() {
        with(homeViewModel) {
            movieListLiveData.observe(viewLifecycleOwner, { movies ->
                moviesAdapter?.submitData(lifecycle, movies)
            })

        }
    }

    override fun getPageTitle() = getString(R.string.romantic_comedy)

    override fun getLayoutId() = R.layout.fragment_home
}
