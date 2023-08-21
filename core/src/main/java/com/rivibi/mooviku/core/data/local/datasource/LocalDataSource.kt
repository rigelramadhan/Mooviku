package com.rivibi.mooviku.core.data.local.datasource

import com.rivibi.mooviku.core.data.local.room.database.MovieDao
import com.rivibi.mooviku.core.data.local.room.entity.MovieEntity
import com.rivibi.mooviku.core.utils.SortFilter
import com.rivibi.mooviku.core.utils.SortQuery
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val movieDao: MovieDao
) {
    fun getMovies(
        sortFilter: SortFilter = SortFilter.Default,
        genreId: Int = -1
    ): Flow<List<MovieEntity>> {
        val query = SortQuery.getSortedMovieByFilter(sortFilter, genreId)
        return movieDao.getMoviesByCategory(query)
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMoviesWithFavorite(movies)

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun setFavorite(movieId: Int, isFavorite: Boolean) =
        movieDao.setFavorite(movieId, isFavorite)

    suspend fun checkFavorite(movieId: Int) = movieDao.isMovieFavorite(movieId)
}