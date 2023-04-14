package com.rivibi.mooviku.core.data.remote.datasource

import com.rivibi.mooviku.core.data.Resource
import com.rivibi.mooviku.core.data.remote.ApiResponse
import com.rivibi.mooviku.core.data.remote.ApiResponseMethod
import com.rivibi.mooviku.core.data.remote.network.ApiService
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject private constructor(
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

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                val response = apiService.getMovieDetail(movieId = movieId)
                val data = DataMapper.mapDetailResponseToDomain(response)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}