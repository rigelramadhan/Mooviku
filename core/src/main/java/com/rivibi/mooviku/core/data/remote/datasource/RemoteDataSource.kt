package com.rivibi.mooviku.core.data.remote.datasource

import com.rivibi.mooviku.core.data.remote.ApiResponse
import com.rivibi.mooviku.core.data.remote.ApiResponseMethod
import com.rivibi.mooviku.core.data.remote.network.ApiService
import com.rivibi.mooviku.core.data.remote.response.GetDetailResponse
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource private constructor(
    private val apiService: ApiService
) {
    fun getNowPlaying(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getNowPlaying(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getPopular(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getPopularMovies(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getTopRated(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getTopRatedMovies(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getDiscover(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getDiscover(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getMovieDetail(movieId: Int): Flow<ApiResponse<GetDetailResponse>> {
        return flow {
            try {
                val response = apiService.getMovieDetail(movieId = movieId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }
}