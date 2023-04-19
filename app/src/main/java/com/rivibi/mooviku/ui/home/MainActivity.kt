package com.rivibi.mooviku.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivibi.mooviku.adapter.MovieListAdapter
import com.rivibi.mooviku.databinding.ActivityMainBinding
import com.rivibi.mooviku.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { mainUiState ->
                    when (mainUiState) {
                        is MainUiState.Success -> {
                            val popularMovies = mainUiState.popularMovies
                            val topRatedMovies = mainUiState.topRatedMovies

                            binding.rvMovieHomePopular.apply {
                                adapter = MovieListAdapter(popularMovies) { movieId ->
                                    val intent =
                                        Intent(this@MainActivity, DetailActivity::class.java)
                                    intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                                }

                                layoutManager = LinearLayoutManager(
                                    this@MainActivity,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }

                            binding.rvMovieHomeTopRated.apply {
                                adapter = MovieListAdapter(topRatedMovies) { movieId ->
                                    val intent =
                                        Intent(this@MainActivity, DetailActivity::class.java)
                                    intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                                }

                                layoutManager = LinearLayoutManager(
                                    this@MainActivity,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }
                        }

                        is MainUiState.Error -> {
                            val errorMessage = mainUiState.exception.message
                            Toast.makeText(this@MainActivity, errorMessage ?: "Error", Toast.LENGTH_SHORT).show()
                        }

                        is MainUiState.Loading -> {
                            Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}