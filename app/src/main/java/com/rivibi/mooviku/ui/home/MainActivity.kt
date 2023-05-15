package com.rivibi.mooviku.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivibi.mooviku.R
import com.rivibi.mooviku.adapter.MovieListAdapter
import com.rivibi.mooviku.databinding.ActivityMainBinding
import com.rivibi.mooviku.ui.detail.DetailActivity
import com.rivibi.mooviku.ui.movielist.MovieListActivity
import com.rivibi.mooviku.ui.utils.MovieQueryTypes
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
        setupButtons()

        binding.progressBar.isVisible = true
        binding.tvMovieHomePopular.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, 24)
            startActivity(intent)
        }
    }

    private fun setupButtons() {
        binding.btnIconSearch.setOnClickListener {
            onSearchRequested()
        }

        binding.btnIconFavorite.setOnClickListener {
            moveToFavoriteActivity()
        }

        binding.btnMorePopular.setOnClickListener {
            val intent = Intent(this, MovieListActivity::class.java)
            intent.putExtra(MovieListActivity.EXTRA_QUERY_TYPE, MovieQueryTypes.POPULAR)
            startActivity(intent)
        }

        binding.btnMoreTopRated.setOnClickListener {
            val intent = Intent(this, MovieListActivity::class.java)
            intent.putExtra(MovieListActivity.EXTRA_QUERY_TYPE, MovieQueryTypes.TOP_RATED)
            startActivity(intent)
        }
    }

    private fun setupView() {
        viewModel.loadData()
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
                                    startActivity(intent)
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
                                    startActivity(intent)
                                }

                                layoutManager = LinearLayoutManager(
                                    this@MainActivity,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }

                            binding.progressBar.isVisible = false
                        }

                        is MainUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.default_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is MainUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

    private fun moveToFavoriteActivity() {
        startActivity(
            Intent(
                this,
                Class.forName("com.rivibi.mooviku.favorite.ui.FavoriteActivity")
            )
        )
    }
}