package com.rivibi.mooviku.core.data.remote.network

import com.rivibi.mooviku.core.data.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY
    ): GetLatestResponse

    @GET("/movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetUpcomingMoviesResponse

    @GET("/movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetUpcomingMoviesResponse

    @GET("/movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetMoviesListResponse

    @GET("/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetMoviesListResponse

    @GET("/movie/discover")
    fun getDiscover(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetMoviesListResponse

    @GET("/genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): GetGenresResponse

    @GET("/movie/{movie_id}")
    fun getMovieDetail(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Path("movie_id") movieId: Int
    ): GetDetailResponse

    @GET("/movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Path("movie_id") movieId: Int,
    ): GetMovieReviewResponse
}