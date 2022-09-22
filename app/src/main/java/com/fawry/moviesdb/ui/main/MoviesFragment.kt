package com.fawry.moviesdb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fawry.moviesdb.R
import com.fawry.moviesdb.databinding.FragmentMoviesBinding
import com.fawry.moviesdb.ui.Event
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    companion object {
        private const val ITEMS_PER_ROW = 2
    }

    private val viewModel: MoviesViewModel by viewModels()
    private val binding get() = _binding!!

    private var _binding: FragmentMoviesBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialMoviesList()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        subscribeToViewStateUpdates(adapter)
    }


    private fun setupRecyclerView(moviesAdapter: MoviesAdapter) {
        binding.moviesRecycler.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(requireContext(), ITEMS_PER_ROW)
            setHasFixedSize(true)
            addOnScrollListener(
                createInfiniteScrollListener(
                    layoutManager
                            as GridLayoutManager
                )
            )
        }
    }

    private fun createInfiniteScrollListener(gridLayoutManager: GridLayoutManager): RecyclerView.OnScrollListener {
        return object : InfiniteScrollListener(
            gridLayoutManager,
        ) {
            override fun loadMoreItems() {
                requestMoreMovies()
            }

            override fun isLoading(): Boolean =
                viewModel.isLoadingMoreMovies

            override fun isLastPage(): Boolean = viewModel.isLastPage
        }
    }

    private fun createAdapter() = MoviesAdapter(MoviesAdapter.MovieClickListener {
        findNavController().navigate(
            MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                it.id
            )
        )
    })

    private fun subscribeToViewStateUpdates(adapter: MoviesAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateScreenState(it, adapter)
                }
            }
        }
    }

    private fun updateScreenState(state: MoviesViewState, adapter: MoviesAdapter) {
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.movies)
        handleNoMoreMovies(state.noMoreMovies)
        handleFailures(state.failure)
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun handleNoMoreMovies(noMoreMovies: Boolean) {
        if (noMoreMovies)
            Snackbar.make(requireView(), R.string.no_more_movies, Snackbar.LENGTH_SHORT).show()

    }

    private fun requestInitialMoviesList() {
        viewModel.onEvent(MoviesEvent.RequestInitialMoviesList)
    }

    private fun requestMoreMovies() {
        viewModel.onEvent(MoviesEvent.RequestMoreMovies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}