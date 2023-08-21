package com.rivibi.mooviku.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModel (
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Loading())
    val uiState: StateFlow<MainUiState> get() = _uiState

    fun loadData() {
        viewModelScope.launch {
            val popularMoviesFlow = movieUseCase.getPopular()
            val topRatedMoviesFlow = movieUseCase.getTopRated()

            combine(popularMoviesFlow, topRatedMoviesFlow) { popular, topRated ->
                MainUiState.Success(
                    popular.data ?: emptyList(),
                    topRated.data ?: emptyList(),
                )
            }.catch {
                it.printStackTrace()
                _uiState.value = MainUiState.Error(it)
            }.collect { mainUiState ->
                _uiState.value = mainUiState
            }
        }
    }
}

sealed class MainUiState {
    data class Success(
        val popularMovies: List<Movie>,
        val topRatedMovies: List<Movie>
    ) : MainUiState()

    data class Error(val exception: Throwable) : MainUiState()
    data class Loading(val message: String? = null) : MainUiState()
}