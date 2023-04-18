package com.rivibi.mooviku.ui.detail

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.rivibi.mooviku.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val movieId: Int by lazy {
        intent.getIntExtra(EXTRA_MOVIE_ID, -1)
    }

    private val actionBar: ActionBar? by lazy {
        supportActionBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {

    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}