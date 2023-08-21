package com.rivibi.mooviku.ui.main.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Genres
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class GenreViewModel (
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<GenreUiState> = MutableStateFlow(GenreUiState.Loading())
    val uiState: StateFlow<GenreUiState> get() = _uiState

    fun loadData(genreId: Int = DEFAULT_GENRE_ID) {
        viewModelScope.launch {
            _uiState.value = GenreUiState.Loading("Loading")
            val genresFlow = movieUseCase.getGenres()
            val popularMoviesFlow = movieUseCase.getPopularMoviesByGenre(genreId = genreId)
            val latestMoviesFlow = movieUseCase.getLatestMoviesByGenre(genreId = genreId)

            combine(genresFlow, popularMoviesFlow, latestMoviesFlow) { genres, popular, latest ->
                GenreUiState.Success(
                    genres.data ?: emptyList(),
                    popular.data ?: emptyList(),
                    latest.data ?: emptyList(),
                )
            }.catch {
                _uiState.value = GenreUiState.Error(it)
            }.collect { genreUiState ->
                _uiState.value = genreUiState
            }
        }
    }

    companion object {
        private const val DEFAULT_GENRE_ID = 28
    }
}

sealed class GenreUiState {
    data class Success(
        val genres: List<Genres>,
        val popularMovies: List<Movie>,
        val latestMovies: List<Movie>,
    ) : GenreUiState()

    data class Error(val throwable: Throwable) : GenreUiState()

    data class Loading(val message: String? = null) : GenreUiState()
}