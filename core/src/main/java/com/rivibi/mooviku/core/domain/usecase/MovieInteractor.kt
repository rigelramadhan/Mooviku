package com.rivibi.mooviku.core.domain.usecase

import com.rivibi.mooviku.core.data.Resource
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.model.Review
import com.rivibi.mooviku.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: IMovieRepository
) : MovieUseCase {
    override fun getNowPlaying(page: Int): Flow<Resource<List<Movie>>> =
        movieRepository.getNowPlaying()

    override fun getPopular(page: Int): Flow<Resource<List<Movie>>> =
        movieRepository.getPopular()

    override fun getTopRated(page: Int): Flow<Resource<List<Movie>>> =
        movieRepository.getTopRated()

    override fun getDiscover(page: Int): Flow<Resource<List<Movie>>> =
        movieRepository.getDiscover()

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> =
        movieRepository.getMovieDetail(movieId)

    override fun getReviews(movieId: Int): Flow<Resource<List<Review>>> =
        movieRepository.getReviews(movieId)

    override fun getMovieRecommendations(movieId: Int): Flow<Resource<List<Movie>>> =
        movieRepository.getMovieRecommendations(movieId)

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        movieRepository.searchMovie(query)

    override fun getFavoriteMovies(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()
    override fun setFavorite(movieId: Int, isFavorite: Boolean) =
        movieRepository.setFavorite(movieId, isFavorite)

}