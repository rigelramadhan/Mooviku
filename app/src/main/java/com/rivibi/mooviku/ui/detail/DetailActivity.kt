package com.rivibi.mooviku.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rivibi.mooviku.adapter.DetailGenreAdapter
import com.rivibi.mooviku.adapter.DetailReviewAdapter
import com.rivibi.mooviku.adapter.MovieListAdapter
import com.rivibi.mooviku.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailViewModel by viewModels()

    private val movieId: Int by lazy {
        intent.getIntExtra(EXTRA_MOVIE_ID, -1)
    }

    private val actionBar: ActionBar? by lazy {
        supportActionBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupView()
    }

    private fun setupView() {
        viewModel.loadData(movieId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { detailUiState ->
                    when (detailUiState) {
                        is DetailUiState.Success -> {
                            val movieDetail = detailUiState.movieDetail
                            val reviews = detailUiState.reviews
                            val movieRecommendations = detailUiState.movieRecommendations

                            if (movieDetail != null) {
                                supportActionBar?.title = movieDetail.title

                                binding.apply {
                                    Glide.with(this@DetailActivity)
                                        .load(movieDetail.posterPath)
                                        .into(imgMovieDetailPoster)

                                    tvDetailReleaseDate.text = movieDetail.releaseDate

                                    rvDetailGenre.apply {
                                        adapter = DetailGenreAdapter(movieDetail.genres)
                                        layoutManager = LinearLayoutManager(
                                            this@DetailActivity,
                                            LinearLayoutManager.HORIZONTAL,
                                            false
                                        )
                                    }

                                    tvDetailMovieDescription.text = movieDetail.overview

                                    val avgRating = DecimalFormat("#.#").format(movieDetail.voteAverage)

                                    tvReviewRating.text = avgRating
                                    progressBarReview.progress = movieDetail.voteAverage.times(10).toInt()

                                    rvReviews.apply {
                                        adapter = DetailReviewAdapter(reviews) {

                                        }

                                        layoutManager = LinearLayoutManager(
                                            this@DetailActivity,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                    }

                                    rvMoreLikeThis.apply {
                                        adapter =
                                            MovieListAdapter(movieRecommendations) { movieId ->
                                                val intent =
                                                    Intent(
                                                        this@DetailActivity,
                                                        DetailActivity::class.java
                                                    )

                                                intent.putExtra(EXTRA_MOVIE_ID, movieId)
                                                startActivity(intent)
                                            }

                                        layoutManager = LinearLayoutManager(
                                            this@DetailActivity,
                                            LinearLayoutManager.HORIZONTAL,
                                            false
                                        )
                                    }
                                }
                            } else {
                                Toast.makeText(this@DetailActivity, "empty", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        is DetailUiState.Error -> {
                            Toast.makeText(this@DetailActivity, "error", Toast.LENGTH_SHORT).show()
                        }

                        is DetailUiState.Loading -> {
                            Toast.makeText(this@DetailActivity, "loading", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}