package com.rivibi.mooviku.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rivibi.mooviku.R
import com.rivibi.mooviku.favorite.adapter.FavoriteMovieAdapter
import com.rivibi.mooviku.favorite.databinding.ActivityFavoriteBinding
import com.rivibi.mooviku.favorite.di.favoriteModule
import com.rivibi.mooviku.ui.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    private val viewModel: FavoriteViewModel by viewModel()

    private val modules by lazy { loadKoinModules(favoriteModule) }

    private fun loadModules() = modules

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadModules()
        setSupportActionBar(binding.toolbar)

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
                                adapter = FavoriteMovieAdapter(data) { movieId ->
                                    val intent =
                                        Intent(this@FavoriteActivity, DetailActivity::class.java)

                                    intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                                    startActivity(intent)
                                }
                                layoutManager =
                                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                            }
                            binding.progressBar.isVisible = false
                        }

                        is FavoriteUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                this@FavoriteActivity,
                                getString(R.string.default_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is FavoriteUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}