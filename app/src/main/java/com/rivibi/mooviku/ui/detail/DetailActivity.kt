package com.rivibi.mooviku.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rivibi.mooviku.R
import com.rivibi.mooviku.adapter.DetailGenreAdapter
import com.rivibi.mooviku.adapter.DetailReviewAdapter
import com.rivibi.mooviku.adapter.MovieListAdapter
import com.rivibi.mooviku.core.domain.model.MovieDetail
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

    private var isFavorite = false
    private var movieDetail: MovieDetail? = null

    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        actionBar = supportActionBar
        actionBar?.title = ""

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
                            val favorite = detailUiState.isFavorite

                            isFavorite = favorite
                            this@DetailActivity.movieDetail = movieDetail

                            if (movieDetail != null) {
                                actionBar?.title = movieDetail.title

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

                                    val avgRating =
                                        DecimalFormat("#.#").format(movieDetail.voteAverage)

                                    tvReviewRating.text = avgRating
                                    progressBarReview.progress =
                                        movieDetail.voteAverage.times(10).toInt()

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
                            }

                            binding.progressBar.isVisible = false
                        }

                        is DetailUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                this@DetailActivity,
                                getString(R.string.default_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is DetailUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (movieDetail != null) {
            viewModel.updateFavorite(movieDetail as MovieDetail)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteState.collect { isFavorite ->
                    menu.findItem(R.id.bookmark).icon = AppCompatResources.getDrawable(
                        this@DetailActivity,
                        if (isFavorite)
                            R.drawable.baseline_bookmark_24
                        else
                            R.drawable.baseline_bookmark_border_24
                    )
                }
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark -> {
                isFavorite = !isFavorite
                viewModel.setFavorite(isFavorite)
            }

            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}