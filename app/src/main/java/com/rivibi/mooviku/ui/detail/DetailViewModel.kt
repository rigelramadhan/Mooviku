package com.rivibi.mooviku.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.model.Review
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loadData(movieId: Int) {
        viewModelScope.launch {
            val movieDetailFlow = movieUseCase.getMovieDetail(movieId)
            val reviewsFlow = movieUseCase.getReviews(movieId)
            val movieRecommendationsFlow = movieUseCase.getMovieRecommendations(movieId)

            combine(
                movieDetailFlow,
                reviewsFlow,
                movieRecommendationsFlow
            ) { movieDetail, reviews, movieRecommendations ->
                DetailUiState.Success(
                    movieDetail.data,
                    reviews.data ?: emptyList(),
                    movieRecommendations.data ?: emptyList(),
                )
            }.catch {
                _uiState.value = DetailUiState.Error(it)
            }.collect { detailUiState ->
                _uiState.value = detailUiState
            }
        }
    }
}

sealed class DetailUiState {
    data class Success(
        val movieDetail: MovieDetail?,
        val reviews: List<Review>,
        val movieRecommendations: List<Movie>
    ) : DetailUiState()

    data class Error(val exception: Throwable) : DetailUiState()
    data class Loading(val message: String? = null) : DetailUiState()
}