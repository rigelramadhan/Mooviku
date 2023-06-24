package com.rivibi.mooviku.core.domain.repository

import com.rivibi.mooviku.core.data.Resource
import com.rivibi.mooviku.core.domain.model.Genres
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getNowPlaying(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getPopular(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getTopRated(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getDiscover(page: Int = 1): Flow<Resource<List<Movie>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>

    fun getReviews(movieId: Int): Flow<Resource<List<Review>>>

    fun getMovieRecommendations(movieId: Int): Flow<Resource<List<Movie>>>

    fun searchMovie(query: String): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun setFavorite(movieId: Int, isFavorite: Boolean)

    fun checkFavorite(movieId: Int): Flow<Boolean>

    suspend fun insertMovies(movies: List<Movie>)

    fun getMoviesByGenre(page: Int, genreId: Int): Flow<Resource<List<Movie>>>

    fun getGenres(): Flow<Resource<List<Genres>>>
}