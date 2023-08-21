package com.rivibi.mooviku.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Loading())
    val uiState: StateFlow<SearchUiState> get() = _uiState

    fun loadData(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading()
            movieUseCase.searchMovie(query)
                .catch {
                    _uiState.value = SearchUiState.Error(it)
                }
                .collect { resource ->
                    val searchUiState = SearchUiState.Success(resource.data ?: emptyList())
                    _uiState.value = searchUiState
                }
        }
    }
}

sealed class SearchUiState {
    data class Success(
        val searchData: List<Movie>
    ) : SearchUiState()

    data class Error(val exception: Throwable) : SearchUiState()

    data class Loading(val message: String? = null) : SearchUiState()
}