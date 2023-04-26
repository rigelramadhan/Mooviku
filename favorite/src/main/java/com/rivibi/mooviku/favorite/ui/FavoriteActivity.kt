package com.rivibi.mooviku.favorite.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rivibi.mooviku.favorite.adapter.FavoriteMovieAdapter
import com.rivibi.mooviku.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is FavoriteUiState.Success -> {
                            val data = uiState.movieList

                            binding.rvMovieFavorite.apply {
                                adapter = FavoriteMovieAdapter(data) {

                                }
                                layoutManager =
                                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                            }
                        }

                        is FavoriteUiState.Error -> {
                            Toast.makeText(this@FavoriteActivity, "ERROR", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is FavoriteUiState.Loading -> {

                        }
                    }
                }
            }
        }
    }
}