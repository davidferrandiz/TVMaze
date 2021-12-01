package com.tvmaze.davidferrandiz.features.showlist.ui

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidferrandiz.presentation.uidata.utils.Resource
import com.davidferrandiz.presentation.vm.ShowsViewModel
import com.tvmaze.davidferrandiz.R
import com.tvmaze.davidferrandiz.databinding.ActivityListBinding
import com.tvmaze.davidferrandiz.features.showdetail.ui.ShowDetailsActivity
import com.tvmaze.davidferrandiz.features.showlist.ui.adapter.ShowsAdapter
import com.tvmaze.davidferrandiz.utils.views.hide
import com.tvmaze.davidferrandiz.utils.views.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_list.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel by viewModels<ShowsViewModel>()
    private lateinit var showsAdapter: ShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()

        setUI()
        setListeners()
        addObservers()

        viewModel.getShows()
    }

    private fun setView() {
        setContentView(R.layout.activity_list)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.shows)
    }

    private fun setUI() {
        with(binding.recycler) {

            val orientation = this.resources.configuration.orientation
            val spanCount =
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    2
                } else {
                    5
                }

            layoutManager = GridLayoutManager(this@ListActivity, spanCount)
            showsAdapter = ShowsAdapter { onShowsClicked(it) }
            adapter = showsAdapter
        }
    }

    private fun onShowsClicked(id: Int) {
        viewModel.goToShowDetails(id)
    }

    private fun setListeners() {
        with(binding) {

            refreshLayout.setOnRefreshListener {
                showsAdapter.refresh()
            }

            showsAdapter.addLoadStateListener { loadState ->

                refreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading

                if (loadState.source.refresh is LoadState.Error) {
                    val error = (loadState.source.refresh as LoadState.Error).error
                    showErrorView(error as Exception)
                }

                genericFeedbackView.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading) {
                    showShows()
                }
            }

            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    floatingActionButton.isVisible = recyclerView.canScrollVertically(-1)
                }
            })

            floatingActionButton.setOnClickListener {
                floatingActionButton.isVisible = false
                recycler.scrollToPosition(0)
            }
        }
    }

    private fun showShows() {
        if (!binding.genericFeedbackView.isVisible) {
            binding.refreshLayout.show()
        } else {
            binding.refreshLayout.hide()
        }
    }

    private fun showErrorView(exception: Exception = Exception()) {
        val error = viewModel.getErrorMessage(exception)
        binding.genericFeedbackView.populate(error, { showsAdapter.retry() })
        binding.refreshLayout.hide()
    }

    private fun addObservers() {
        with(viewModel) {

            lifecycleScope.launchWhenStarted {
                showsStateFlow.collectLatest {
                    binding.genericFeedbackView.isVisible = false
                    showsAdapter.submitData(it)
                }
            }

            lifecycleScope.launch {
                showDetailsFlow.collect {

                    binding.progressLayout.isVisible = it is Resource.Loading

                    when (it) {
                        is Resource.Error -> {
                            Toast.makeText(this@ListActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            startActivity(ShowDetailsActivity.getIntent(this@ListActivity,
                                it.data!!))
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}