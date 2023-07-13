package com.rivibi.mooviku.core.data.local.room.database

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.rivibi.mooviku.core.data.local.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMoviesByCategory(query: SimpleSQLiteQuery): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMoviesWithFavorite(movies: List<MovieEntity>) {
        val filteredMovies = movies.map { movie ->
            if (isMovieFavorite(movie.id)) {
                movie.favorite = true
            }

            movie
        }

        insertMovies(filteredMovies)
    }

    @Query("SELECT * FROM movies WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET favorite = :isFavorite WHERE id = :movieId")
    fun setFavorite(movieId: Int, isFavorite: Boolean)

    @Query("SELECT EXISTS (SELECT * FROM movies WHERE id = :movieId AND favorite = 1)")
    suspend fun isMovieFavorite(movieId: Int): Boolean
}