package com.rivibi.mooviku.core.data.local.datasource

import com.rivibi.mooviku.core.data.local.MovieCategory
import com.rivibi.mooviku.core.data.local.room.database.MovieDao
import com.rivibi.mooviku.core.data.local.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject private constructor(
    private val movieDao: MovieDao
) {
    fun getPopularMovies(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.Popular.name)
    }

    fun getNowPlaying(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.NowPlaying.name)
    }

    fun getTopRated(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.TopRated.name)
    }

    fun getDiscover(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.Discover.name)
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)
}