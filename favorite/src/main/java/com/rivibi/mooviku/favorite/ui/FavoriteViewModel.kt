package com.rivibi.mooviku.favorite.ui

import androidx.lifecycle.ViewModel
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<FavoriteUiState> =
        MutableStateFlow(FavoriteUiState.Loading())
    val uiState: StateFlow<FavoriteUiState> get() = _uiState

    init {
        loadData()
    }

    private fun loadData() {

    }
}

sealed class FavoriteUiState {
    data class Success(val movieList: List<Movie>) : FavoriteUiState()
    data class Error(val throwable: Throwable) : FavoriteUiState()
    data class Loading(val message: String? = null) : FavoriteUiState()
}
