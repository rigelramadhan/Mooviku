package com.rivibi.mooviku.ui.movielist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rivibi.mooviku.adapter.DynamicMovieListAdapter
import com.rivibi.mooviku.databinding.ActivityMovieListBinding
import com.rivibi.mooviku.ui.detail.DetailActivity
import com.rivibi.mooviku.ui.utils.MovieQueryTypes
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListActivity : AppCompatActivity() {

    private val binding: ActivityMovieListBinding by lazy {
        ActivityMovieListBinding.inflate(layoutInflater)
    }

    private val viewModel: MovieListViewModel by viewModel()

    private val movieQueryType: String by lazy {
        intent.getStringExtra(EXTRA_QUERY_TYPE) ?: MovieQueryTypes.DISCOVERY
    }

    private var recommendationMovieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (movieQueryType == MovieQueryTypes.RECOMMENDATION) {
            recommendationMovieId = intent.getIntExtra(EXTRA_RECOMMENDATION_MOVIE_ID, -1)
        }

        setupView()
    }

    private fun setupView() {
        viewModel.loadData(movieQueryType, recommendationMovieId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is MovieListUiState.Success -> {
                            binding.progressBar.isVisible = false

                            val data = uiState.movieListData

                            binding.rvListMovie.apply {
                                adapter = DynamicMovieListAdapter(data) { movieId ->
                                    val intent =
                                        Intent(this@MovieListActivity, DetailActivity::class.java)
                                    intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                                    startActivity(intent)
                                }

                                layoutManager =
                                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                            }
                        }

                        is MovieListUiState.Error -> {
                            binding.progressBar.isVisible = false
                        }

                        is MovieListUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_QUERY_TYPE = "extra_query_type"
        const val EXTRA_RECOMMENDATION_MOVIE_ID = "extra_recommendation_movie_id"
    }
}