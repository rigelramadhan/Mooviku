package com.rivibi.mooviku.core.data.remote.network

import com.rivibi.mooviku.core.data.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY
    ): GetLatestResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetUpcomingMoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetUpcomingMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetMoviesListResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetMoviesListResponse

    @GET("movie/discover")
    suspend fun getDiscover(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("page") page: Int
    ): GetMoviesListResponse

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): GetGenresResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY
    ): GetDetailResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY
    ): GetMoviesListResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): GetMovieReviewResponse

    @GET("search/movie")
    suspend fun getSearch(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int
    ): GetMoviesListResponse
}