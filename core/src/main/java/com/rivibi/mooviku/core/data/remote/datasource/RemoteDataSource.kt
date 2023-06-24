package com.rivibi.mooviku.core.data.remote.datasource

import android.util.Log
import com.rivibi.mooviku.core.data.Resource
import com.rivibi.mooviku.core.data.local.MovieCategory
import com.rivibi.mooviku.core.data.remote.ApiResponse
import com.rivibi.mooviku.core.data.remote.ApiResponseMethod
import com.rivibi.mooviku.core.data.remote.network.ApiService
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.domain.model.Genres
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.model.Review
import com.rivibi.mooviku.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
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
        return channelFlow {
            try {
                val response = apiService.getPopularMovies(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    send(ApiResponse.Success(data))
                } else {
                    send(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                Log.e("MOOVIKU_ERROR", e.message.toString())
                send(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getTopRated(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return channelFlow {
            try {
                val response = apiService.getTopRatedMovies(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    send(ApiResponse.Success(data))
                } else {
                    send(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                Log.e("MOOVIKU_ERROR", e.message.toString())
                send(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getDiscover(
        page: Int = 1,
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
                Log.e("MOOVIKU_ERROR", e.message.toString())
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getGenres(): Flow<Resource<List<Genres>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getGenreList()
                val data = response.genres.map { Genres(id = it.id, name = it.name) }

                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getMoviesByGenre(
        page: Int = 1,
        genreId: Int,
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getDiscoverWithGenres(page = page, genreId = genreId)
                val data =
                    DataMapper.mapResponseToEntity(response.results, MovieCategory.Genre.category)

                if (data.isNotEmpty()) {
                    emit(Resource.Success(DataMapper.mapEntityToDomain(data)))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return channelFlow {
            send(Resource.Loading())
            try {
                val response = apiService.getMovieDetail(movieId = movieId)
                val data = DataMapper.mapDetailResponseToDomain(response)
                send(Resource.Success(data))
            } catch (e: Exception) {
                send(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getMovieReviews(movieId: Int): Flow<Resource<List<Review>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getMovieReviews(movieId = movieId)
                val data = DataMapper.mapReviewsResponseToDomain(response.results)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getMovieRecommendations(movieId: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getMovieRecommendations(movieId = movieId)
                val entities = DataMapper.mapResponseToEntity(response.results, category = "Others")
                val data = DataMapper.mapEntityToDomain(entities)

                if (data.isNotEmpty()) {
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(ApiResponseMethod.ERROR_404.name))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    fun searchMovies(query: String, page: Int = 1): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getSearch(query = query, page = page)
                val entities = DataMapper.mapResponseToEntity(response.results, category = "Others")
                val data = DataMapper.mapEntityToDomain(entities)

                if (data.isNotEmpty()) {
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(ApiResponseMethod.ERROR_404.name))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}