package com.rivibi.mooviku.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rivibi.mooviku.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.action == Intent.ACTION_SEARCH) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                performSearch(query)
            }
        }
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { searchUiState ->
                    when (searchUiState) {
                        is SearchUiState.Success -> {
                            val data = searchUiState.searchData

                        }

                        is SearchUiState.Error -> {

                        }

                        is SearchUiState.Loading -> {

                        }
                    }
                }
            }
        }
    }
}