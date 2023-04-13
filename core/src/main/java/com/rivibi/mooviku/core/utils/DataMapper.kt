package com.rivibi.mooviku.core.utils

import com.rivibi.mooviku.core.data.local.room.entity.MovieEntity
import com.rivibi.mooviku.core.data.remote.response.GetDetailResponse
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.domain.model.*

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

    fun mapDetailResponseToDomain(input: GetDetailResponse): MovieDetail =
        MovieDetail(
            originalLanguage = input.originalLanguage,
            imdbId = input.imdbId,
            video = input.video,
            title = input.title,
            backdropPath = input.backdropPath,
            revenue = input.revenue,
            genres = input.genres.map { Genres(it.name, it.id) },
            popularity = input.popularity,
            productionCountries = input.productionCountries.map {
                ProductionCountries(
                    it.iso31661,
                    it.name
                )
            },
            id = input.id,
            voteCount = input.voteCount,
            budget = input.budget,
            overview = input.overview,
            originalTitle = input.originalTitle,
            runtime = input.runtime,
            posterPath = input.posterPath,
            spokenLanguages = input.spokenLanguages.map {
                SpokenLanguages(
                    it.name,
                    it.iso6391,
                    it.englishName
                )
            },
            productionCompanies = input.productionCompanies.map {
                ProductionCompanies(
                    it.logoPath,
                    it.name,
                    it.id,
                    it.originCountry
                )
            },
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            belongsToCollection = BelongsToCollection(
                input.belongsToCollection.backdropPath,
                input.belongsToCollection.name,
                input.belongsToCollection.id,
                input.belongsToCollection.posterPath
            ),
            tagline = input.tagline,
            adult = input.adult,
            homepage = input.homepage,
            status = input.status
        )
}