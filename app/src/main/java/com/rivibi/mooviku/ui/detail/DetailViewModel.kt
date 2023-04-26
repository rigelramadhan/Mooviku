package com.rivibi.mooviku.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.model.Review
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import com.rivibi.mooviku.core.utils.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading())

    val uiState: StateFlow<DetailUiState> get() = _uiState

    private val _favoriteState = MutableStateFlow(false)
    val favoriteState: StateFlow<Boolean> get() = _favoriteState

    fun loadData(movieId: Int) {
        viewModelScope.launch {
            val movieDetailFlow = movieUseCase.getMovieDetail(movieId)
            val reviewsFlow = movieUseCase.getReviews(movieId)
            val movieRecommendationsFlow = movieUseCase.getMovieRecommendations(movieId)
            val isFavoriteFlow = movieUseCase.checkFavorite(movieId)

            combine(
                movieDetailFlow,
                reviewsFlow,
                movieRecommendationsFlow,
                isFavoriteFlow
            ) { movieDetail, reviews, movieRecommendations, isFavorite ->
                DetailUiState.Success(
                    movieDetail.data,
                    reviews.data ?: emptyList(),
                    movieRecommendations.data ?: emptyList(),
                    isFavorite
                )
            }.catch {
                it.printStackTrace()
                _uiState.value = DetailUiState.Error(it)
            }.collect { detailUiState ->
                _uiState.value = detailUiState
                _favoriteState.value = detailUiState.isFavorite
            }
        }
    }

    fun updateFavorite(movieDetail: MovieDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = DataMapper.mapDetailToMovieListItem(movieDetail, _favoriteState.value)
            movieUseCase.insertMovies(listOf(movie))
            movieUseCase.setFavorite(movie.id, movie.favorite)
            Log.d("MOOVIKU_INFO", "Updated favorite on: ${movieDetail.originalTitle}")
        }
    }

    fun setFavorite(isFavorite: Boolean) {
        _favoriteState.value = isFavorite
    }
}

sealed class DetailUiState {
    data class Success(
        val movieDetail: MovieDetail?,
        val reviews: List<Review>,
        val movieRecommendations: List<Movie>,
        val isFavorite: Boolean,
    ) : DetailUiState()

    data class Error(val exception: Throwable) : DetailUiState()
    data class Loading(val message: String? = null) : DetailUiState()
}