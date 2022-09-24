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
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fawry.moviesdb.databinding.FragmentMoviesBinding
import com.fawry.moviesdb.domain.model.category.Category
import com.fawry.moviesdb.domain.model.category.PopularCategory
import com.fawry.moviesdb.domain.model.category.TopRatedCategory
import com.fawry.moviesdb.domain.model.category.UpcomingCategory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private val binding get() = _binding!!

    private var _binding: FragmentMoviesBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val popularAdapter = createAdapter(PopularCategory())
        val topRatedAdapter = createAdapter(TopRatedCategory())
        val upcomingAdapter = createAdapter(UpcomingCategory())
        setupPopularRecyclerView(popularAdapter)
        setupTopRatedRecyclerView(topRatedAdapter)
        setupUpcomingRecyclerView(upcomingAdapter)
        subscribeToTopRatedViewStateUpdates(topRatedAdapter)
        subscribeToPopularViewStateUpdates(popularAdapter)
        subscribeToUpcomingViewStateUpdates(upcomingAdapter)
    }

    private fun setupPopularRecyclerView(moviesAdapter: MoviesAdapter) {
        binding.popularRecycler.apply {
            adapter = moviesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
        }
    }

    private fun setupTopRatedRecyclerView(moviesAdapter: MoviesAdapter) {
        binding.topmoviesRecycler.apply {
            adapter = moviesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
        }
    }

    private fun setupUpcomingRecyclerView(moviesAdapter: MoviesAdapter) {
        binding.upcomingRecycler.apply {
            adapter = moviesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
        }
    }

    private fun createAdapter(category: Category) = MoviesAdapter(
        MoviesAdapter.MovieClickListener {
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    it.id
                )
            )
        }
    ).also {

        it.addLoadStateListener { loadState ->
            category.setupProgress(binding, it.itemCount < 1)

            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Snackbar.make(
                    requireView(),
                    it.error.localizedMessage.toString(),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun subscribeToPopularViewStateUpdates(adapter: MoviesAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popular.collect {
                    it?.let { it1 -> adapter.submitData(it1) }
                }
            }
        }
    }

    private fun subscribeToTopRatedViewStateUpdates(adapter: MoviesAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topRatedState.collect {
                    it?.let { it1 -> adapter.submitData(it1) }
                }
            }
        }
    }

    private fun subscribeToUpcomingViewStateUpdates(adapter: MoviesAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.upcomingState.collect {
                    it?.let { it1 -> adapter.submitData(it1) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
