package com.android.home.presentation.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.core.extensions.EMPTY
import com.android.core.extensions.showToast
import com.android.core.extensions.textChanges
import com.android.core.platform.BaseFragment
import com.android.core.utils.AppUtils
import com.android.domain.model.Content
import com.android.home.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : BaseFragment() {
    private val searchViewModel by viewModel<SearchViewModel>()
    private val appUtils by inject<AppUtils>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        initUI()
    }

    private fun initUI() {
        handleEditTextChanges()
        imageSearchCancel?.setOnClickListener {
            editTextSearch?.setText(String.EMPTY)
        }
    }

    private fun handleEditTextChanges() {
        editTextSearch?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                appUtils.hideSoftKeyboard(view)
                searchViewModel.search(view.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        editTextSearch?.textChanges()
            ?.filterNot { it.isNullOrBlank() || it.trim().length < SEARCH_MINIMUM_LENGTH }
            ?.debounce(DEBOUNCE_TIME)
            ?.onEach { query ->
                searchViewModel.search(query = query.toString())
            }
            ?.launchIn(lifecycleScope)
    }

    private fun observeLiveData() {
        with(searchViewModel) {
            progressBarLiveData.observe(viewLifecycleOwner, { isVisible ->
                progressBarSearch?.isVisible = isVisible
            })
            errorLiveData.observe(viewLifecycleOwner, { error ->
                context.showToast(error)
            })
            movieListLiveData.observe(viewLifecycleOwner, { result ->
                initHomeRecyclerView(result.first, result.second)
            })
        }
    }

    private fun initHomeRecyclerView(query: String, movieList: List<Content>) {

        context?.let { context ->
            recyclerViewSearch?.run {
                layoutManager = GridLayoutManager(
                    context,
                    resources.getInteger(R.integer.movies_grid_column_count),
                    GridLayoutManager.VERTICAL,
                    false
                )
                adapter = SearchRecyclerViewAdapter(
                    context = context,
                    query = query,
                    items = movieList
                )
            }
        }
    }

    override fun isToolbarRequired() = false

    override fun getLayoutId() = R.layout.fragment_search

    companion object {
        private const val DEBOUNCE_TIME = 300L
        private const val SEARCH_MINIMUM_LENGTH = 3
    }
}
