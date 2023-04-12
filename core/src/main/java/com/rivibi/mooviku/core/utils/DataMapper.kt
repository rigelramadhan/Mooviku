package com.rivibi.mooviku.core.utils

import com.rivibi.mooviku.core.data.local.room.entity.MovieEntity
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.domain.model.Movie

object DataMapper {
    fun mapResponseToEntity(input: List<MoviesItem>, category: String): List<MovieEntity> =
        input.map {
            MovieEntity(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = it.genreIds,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount,
                category = category
            )
        }

    fun mapEntityToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = it.genreIds,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount,
                category = it.category
            )
        }
}