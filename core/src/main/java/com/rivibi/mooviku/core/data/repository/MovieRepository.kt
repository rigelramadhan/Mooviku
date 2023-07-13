package com.rivibi.mooviku.core.data.repository

import com.rivibi.mooviku.core.data.NetworkBoundResource
import com.rivibi.mooviku.core.data.Resource
import com.rivibi.mooviku.core.data.local.datasource.LocalDataSource
import com.rivibi.mooviku.core.data.remote.ApiResponse
import com.rivibi.mooviku.core.data.remote.datasource.RemoteDataSource
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.domain.model.Genres
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.model.Review
import com.rivibi.mooviku.core.domain.repository.IMovieRepository
import com.rivibi.mooviku.core.utils.AppExecutors
import com.rivibi.mooviku.core.utils.DataMapper
import com.rivibi.mooviku.core.utils.SortAttribute
import com.rivibi.mooviku.core.utils.SortFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IMovieRepository {
    override fun getNowPlaying(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
//                TODO: Replace with the correct filters.
                return localDataSource.getMovies(SortFilter.Default)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getNowPlaying()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(DataMapper.mapResponseToEntity(data))
            }

            override fun shouldFetch(data: List<Movie>?) = true
        }.asFlow()

    override fun getPopular(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies(SortFilter.Popularity)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getPopular()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(DataMapper.mapResponseToEntity(data))
            }

            override fun shouldFetch(data: List<Movie>?) = true
        }.asFlow()

    override fun getTopRated(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies(SortFilter.TopRated)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getTopRated()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(DataMapper.mapResponseToEntity(data))
            }

            override fun shouldFetch(data: List<Movie>?) = true
        }.asFlow()

    override fun getDiscover(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies(SortFilter.Default)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getDiscover()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(DataMapper.mapResponseToEntity(data))
            }

            override fun shouldFetch(data: List<Movie>?) = true

        }.asFlow()

    override fun getGenres(): Flow<Resource<List<Genres>>> = remoteDataSource.getGenres()

    override fun getPopularMoviesByGenre(page: Int, genreId: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies(SortFilter.Popularity, genreId)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getMoviesByGenre(page, genreId, SortAttribute.SORT_POPULARITY)
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(
                    DataMapper.mapResponseToEntity(data)
                )
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

        }.asFlow()

    override fun getLatestMoviesByGenre(page: Int, genreId: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies(SortFilter.Latest, genreId)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getMoviesByGenre(page, genreId, SortAttribute.SORT_LATEST)
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(
                    DataMapper.mapResponseToEntity(data)
                )
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

        }.asFlow()

    override fun getMoviesByGenre(page: Int, genreId: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies(genreId = genreId)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getMoviesByGenre(page, genreId)
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(
                    DataMapper.mapResponseToEntity(data)
                )
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

        }.asFlow()

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> =
        remoteDataSource.getMovieDetail(movieId)

    override fun getReviews(movieId: Int): Flow<Resource<List<Review>>> =
        remoteDataSource.getMovieReviews(movieId)

    override fun getMovieRecommendations(movieId: Int): Flow<Resource<List<Movie>>> =
        remoteDataSource.getMovieRecommendations(movieId)

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        remoteDataSource.searchMovies(query)

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavorite(movieId: Int, isFavorite: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavorite(movieId, isFavorite)
        }
    }

    override fun checkFavorite(movieId: Int): Flow<Boolean> = flow {
        emit(localDataSource.checkFavorite(movieId))
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        localDataSource.insertMovies(DataMapper.mapDomainToEntity(movies))
    }
}

