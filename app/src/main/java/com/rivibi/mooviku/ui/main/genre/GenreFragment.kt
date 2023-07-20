package com.rivibi.mooviku.ui.main.genre

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivibi.mooviku.R
import com.rivibi.mooviku.adapter.MovieListAdapter
import com.rivibi.mooviku.adapter.SmallGenreAdapter
import com.rivibi.mooviku.databinding.FragmentGenreBinding
import com.rivibi.mooviku.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GenreFragment : Fragment() {

    private val viewModel: GenreViewModel by viewModels()

    private val binding: FragmentGenreBinding by lazy {
        FragmentGenreBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupButton()
    }

    private fun setupButton() {
//        TODO: Fix the "view more" query and implement the buttons
    }

    private fun setupView() {
        viewModel.loadData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is GenreUiState.Success -> {
                            binding.progressBar.isVisible = false

                            val genres = uiState.genres
                            val popularMovies = uiState.popularMovies
                            val latestMovies = uiState.latestMovies

                            if (genres.isNotEmpty()) {
                                binding.rvHomeGenre.isVisible = true
                                binding.rvHomeGenre.apply {
                                    adapter = SmallGenreAdapter(genres) { genreId ->
                                        viewModel.loadData(genreId)
                                    }
                                    layoutManager = LinearLayoutManager(
                                        requireContext(),
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                }
                            } else {
                                binding.rvHomeGenre.isVisible = false
                            }

                            if (popularMovies.isNotEmpty()) {
                                binding.tvMovieHomePopular.visibility = View.VISIBLE
                                binding.rvMovieHomePopular.apply {
                                    adapter = MovieListAdapter(popularMovies) { movieId ->
                                        navigateToDetail(movieId)
                                    }
                                    layoutManager = LinearLayoutManager(
                                        requireContext(),
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                }
                            } else {
                                binding.tvMovieHomePopular.visibility = View.INVISIBLE
                            }

                            if (latestMovies.isNotEmpty()) {
                                binding.tvMovieHomeLatest.visibility = View.VISIBLE
                                binding.rvMovieHomeLatest.apply {
                                    adapter = MovieListAdapter(latestMovies) { movieId ->
                                        navigateToDetail(movieId)
                                    }
                                    layoutManager = LinearLayoutManager(
                                        requireContext(),
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                }
                            } else {
                                binding.tvMovieHomeLatest.visibility = View.INVISIBLE
                            }
                        }

                        is GenreUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.default_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is GenreUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetail(movieId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
        requireContext().startActivity(intent)
    }
}