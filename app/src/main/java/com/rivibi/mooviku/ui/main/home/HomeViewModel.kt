package com.rivibi.mooviku.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    fun loadData() {
        viewModelScope.launch {
            val popularMoviesFlow = movieUseCase.getPopular()
            val topRatedMoviesFlow = movieUseCase.getTopRated()

            combine(popularMoviesFlow, topRatedMoviesFlow) { popular, topRated ->
                HomeUiState.Success(
                    popular.data ?: emptyList(),
                    topRated.data ?: emptyList(),
                )
            }.catch {
                it.printStackTrace()
                _uiState.value = HomeUiState.Error(it)
            }.collect { homeUiState ->
                _uiState.value = homeUiState
            }
        }
    }
}

sealed class HomeUiState {
    data class Success(
        val popularMovies: List<Movie>,
        val topRatedMovies: List<Movie>
    ) : HomeUiState()

    data class Error(val exception: Throwable) : HomeUiState()
    data class Loading(val message: String? = null) : HomeUiState()
}