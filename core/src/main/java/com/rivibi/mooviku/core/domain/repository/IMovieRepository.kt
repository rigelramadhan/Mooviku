package com.rivibi.mooviku.core.domain.repository

import com.rivibi.mooviku.core.data.Resource
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getNowPlaying(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getPopular(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getTopRated(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getDiscover(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>
}