package com.rivibi.mooviku.core.utils

import com.rivibi.mooviku.core.data.ImageConfig
import com.rivibi.mooviku.core.data.local.room.entity.GenreList
import com.rivibi.mooviku.core.data.local.room.entity.MovieEntity
import com.rivibi.mooviku.core.data.remote.response.GetDetailResponse
import com.rivibi.mooviku.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.data.remote.response.ReviewItem
import com.rivibi.mooviku.core.domain.model.AuthorDetails
import com.rivibi.mooviku.core.domain.model.Genres
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.domain.model.MovieDetail
import com.rivibi.mooviku.core.domain.model.ProductionCompanies
import com.rivibi.mooviku.core.domain.model.ProductionCountries
import com.rivibi.mooviku.core.domain.model.Review
import com.rivibi.mooviku.core.domain.model.SpokenLanguages

object DataMapper {
    private fun generateImageLink(imgSize: String, path: String): String =
        "${ImageConfig.IMAGE_URL}$imgSize/$path"

    fun mapResponseToEntity(input: List<MoviesItem>, category: String): List<MovieEntity> =
        input.map {
            MovieEntity(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = GenreList(it.genreIds),
                posterPath = generateImageLink(ImageConfig.PosterSize.W500.size, it.posterPath),
                backdropPath = if (it.backdropPath != null) generateImageLink(
                    ImageConfig.BackdropSize.W700.size,
                    it.backdropPath
                ) else it.posterPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount,
                category = category,
                favorite = false
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
                genreIds = it.genreIds.genreIds,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath ,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount,
                category = it.category,
                favorite = it.favorite
            )
        }

    fun mapDomainToEntity(input: List<Movie>): List<MovieEntity> =
        input.map {
            MovieEntity(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = GenreList(it.genreIds),
                posterPath = it.posterPath,
                backdropPath = it.backdropPath ,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount,
                category = it.category,
                favorite = it.favorite
            )
        }

    fun mapDetailToMovieListItem(input: MovieDetail, isFavorite: Boolean): Movie =
        Movie(
            overview = input.overview,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            video = input.video,
            title = input.title,
            genreIds = input.genres.map { it.id },
            posterPath = input.posterPath,
            backdropPath = input.backdropPath ,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            id = input.id,
            adult = input.adult,
            voteCount = input.voteCount,
            category = "",
            favorite = isFavorite
        )

    fun mapDetailResponseToDomain(input: GetDetailResponse): MovieDetail =
        MovieDetail(
            originalLanguage = input.originalLanguage,
            imdbId = input.imdbId,
            video = input.video,
            title = input.title,
            backdropPath = if (input.backdropPath != null) generateImageLink(
                ImageConfig.BackdropSize.W700.size,
                input.backdropPath
            ) else input.posterPath,
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
            posterPath = generateImageLink(
                ImageConfig.PosterSize.W500.size,
                input.posterPath
            ),
            spokenLanguages = input.spokenLanguages.map {
                SpokenLanguages(
                    it.name,
                    it.iso6391,
                    it.englishName
                )
            },
            productionCompanies = input.productionCompanies.map {
                ProductionCompanies(
                    generateImageLink(ImageConfig.LogoSize.W300.size, it.logoPath),
                    it.name,
                    it.id,
                    it.originCountry
                )
            },
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            tagline = input.tagline,
            adult = input.adult,
            homepage = input.homepage,
            status = input.status
        )

    fun mapReviewsResponseToDomain(input: List<ReviewItem>) = input.map {
        val authorDetails = AuthorDetails(

            avatarPath = if (it.authorDetails.avatarPath != null)
                generateImageLink(
                    ImageConfig.ProfileSize.W185.size,
                    it.authorDetails.avatarPath,
                )
            else null,
            name = it.authorDetails.name,
            rating = it.authorDetails.rating,
            username = it.authorDetails.username
        )

        Review(
            authorDetails = authorDetails,
            updatedAt = it.updatedAt,
            author = it.author,
            createdAt = it.createdAt,
            id = it.id,
            content = it.content,
            url = it.url
        )
    }
}