package com.rivibi.mooviku.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rivibi.mooviku.R
import com.rivibi.mooviku.adapter.DynamicMovieListAdapter
import com.rivibi.mooviku.databinding.ActivitySearchBinding
import com.rivibi.mooviku.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        viewModel.loadData(query)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { searchUiState ->
                    when (searchUiState) {
                        is SearchUiState.Success -> {
                            val data = searchUiState.searchData

                            with(binding) {
                                rvSearchMovie.apply {
                                    adapter = DynamicMovieListAdapter(data) { movieId ->
                                        val intent =
                                            Intent(this@SearchActivity, DetailActivity::class.java)
                                        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                                        startActivity(intent)
                                    }
                                    layoutManager =
                                        StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                                }
                            }

                            binding.progressBar.isVisible = false
                        }

                        is SearchUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                this@SearchActivity,
                                getString(R.string.default_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is SearchUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        viewModel.loadData(query)
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val EXTRA_QUERY = "extra_query"
    }
}