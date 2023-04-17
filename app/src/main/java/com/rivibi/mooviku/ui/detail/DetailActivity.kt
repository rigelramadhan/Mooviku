package com.rivibi.mooviku.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rivibi.mooviku.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


}