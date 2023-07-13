
package com.rivibi.mooviku.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivibi.mooviku.R
import com.rivibi.mooviku.adapter.MovieListAdapter
import com.rivibi.mooviku.databinding.FragmentHomeBinding
import com.rivibi.mooviku.ui.detail.DetailActivity
import com.rivibi.mooviku.ui.movielist.MovieListActivity
import com.rivibi.mooviku.ui.utils.MovieQueryTypes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupButtons()
        setupMenu()
    }

    private fun setupButtons() {
//        binding.btnIconSearch.setOnClickListener {
//            requireActivity().onSearchRequested()
//        }
//
//        binding.btnIconFavorite.setOnClickListener {
//            moveToFavoriteActivity()
//        }

        binding.btnMorePopular.setOnClickListener {
            val intent = Intent(requireContext(), MovieListActivity::class.java)
            intent.putExtra(MovieListActivity.EXTRA_QUERY_TYPE, MovieQueryTypes.POPULAR)
            startActivity(intent)
        }

        binding.btnMoreTopRated.setOnClickListener {
            val intent = Intent(requireContext(), MovieListActivity::class.java)
            intent.putExtra(MovieListActivity.EXTRA_QUERY_TYPE, MovieQueryTypes.TOP_RATED)
            startActivity(intent)
        }
    }

    private fun setupView() {
        viewModel.loadData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { homeUiState ->
                    when (homeUiState) {
                        is HomeUiState.Success -> {
                            val popularMovies = homeUiState.popularMovies
                            val topRatedMovies = homeUiState.topRatedMovies

                            binding.rvMovieHomePopular.apply {
                                adapter = MovieListAdapter(popularMovies) { movieId ->
                                    val intent =
                                        Intent(requireContext(), DetailActivity::class.java)
                                    intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                                    startActivity(intent)
                                }

                                layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }

                            binding.rvMovieHomeTopRated.apply {
                                adapter = MovieListAdapter(topRatedMovies) { movieId ->
                                    val intent =
                                        Intent(requireContext(), DetailActivity::class.java)
                                    intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                                    startActivity(intent)
                                }

                                layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }

                            binding.progressBar.isVisible = false
                        }

                        is HomeUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.default_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is HomeUiState.Loading -> {
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
                requireContext(),
                Class.forName("com.rivibi.mooviku.favorite.ui.FavoriteActivity")
            )
        )
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        })
    }
}