package com.rivibi.mooviku.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import com.rivibi.mooviku.ui.utils.MovieQueryTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MovieListUiState> =
        MutableStateFlow(MovieListUiState.Loading())
    val uiState: StateFlow<MovieListUiState> get() = _uiState

    fun loadData(movieQueryType: String, movieId: Int? = null) {
        viewModelScope.launch {
            val movieFlow = when (movieQueryType) {
                MovieQueryTypes.POPULAR -> movieUseCase.getPopular()
                MovieQueryTypes.TOP_RATED -> movieUseCase.getTopRated()
                MovieQueryTypes.RECOMMENDATION -> movieUseCase.getMovieRecommendations(movieId ?: 1)
                else -> movieUseCase.getDiscover()
            }

            movieFlow
                .catch {
                    _uiState.value = MovieListUiState.Error(it)
                }
                .collect {
                    val movieListUiState = MovieListUiState.Success(it.data ?: emptyList())
                    _uiState.value = movieListUiState
                }
        }
    }
}

sealed class MovieListUiState {
    data class Success(
        val movieListData: List<Movie>
    ) : MovieListUiState()

    data class Error(val exception: Throwable) : MovieListUiState()

    data class Loading(val message: String? = null) : MovieListUiState()
}