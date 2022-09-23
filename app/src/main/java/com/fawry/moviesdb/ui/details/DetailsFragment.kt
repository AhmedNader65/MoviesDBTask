package com.fawry.moviesdb.ui.details

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
import androidx.navigation.fragment.navArgs
import com.fawry.moviesdb.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private val binding get() = _binding!!
    private val args by navArgs<DetailsFragmentArgs>()
    private var _binding: FragmentDetailsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        viewModel.getMovieDetails(args.movieId)

    }

    private fun setupUI() {
        subscribeToViewStateUpdates()

    }

    private fun subscribeToViewStateUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateScreenState(it)
                }
            }
        }
    }

    private fun updateScreenState(state: MovieDetailsViewState) {
        binding.progressBar.isVisible = state.loading
        state.movie?.let { binding.movie = state.movie }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}