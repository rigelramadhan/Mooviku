package com.rivibi.mooviku.ui.detail

import androidx.lifecycle.ViewModel
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading())

    private val uiState: StateFlow<DetailUiState> get() = _uiState
}

sealed class DetailUiState {
    data class Success(
        val movieDetail: MovieDetail,
    ) : DetailUiState()

    data class Error(val exception: Throwable) : DetailUiState()
    data class Loading(val message: String? = null) : DetailUiState()
}