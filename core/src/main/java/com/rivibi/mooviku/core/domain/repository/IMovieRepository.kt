package com.rivibi.mooviku.core.domain.repository

import com.rivibi.mooviku.core.data.remote.ApiResponse
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getNowPlaying(page: Int = 1): Flow<ApiResponse<List<MoviesItem>>>

    fun getPopular(page: Int = 1): Flow<ApiResponse<List<MoviesItem>>>

    fun getTopRated(page: Int = 1): Flow<ApiResponse<List<MoviesItem>>>

    fun getDiscover(page: Int = 1): Flow<ApiResponse<List<MoviesItem>>>

    fun getMovieDetail(movieId: Int)
}