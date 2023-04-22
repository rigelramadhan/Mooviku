package com.rivibi.mooviku.core.domain.model

data class MovieDetail(
    val originalLanguage: String,
    val imdbId: String?,
    val video: Boolean,
    val title: String,
    val backdropPath: String?,
    val revenue: Long,
    val genres: List<Genres>,
    val popularity: Double,
    val productionCountries: List<ProductionCountries>,
    val id: Int,
    val voteCount: Int,
    val budget: Long,
    val overview: String,
    val originalTitle: String,
    val runtime: Int,
    val posterPath: String?,
    val spokenLanguages: List<SpokenLanguages>,
    val productionCompanies: List<ProductionCompanies>,
    val releaseDate: String,
    val voteAverage: Double,
    val tagline: String,
    val adult: Boolean,
    val homepage: String,
    val status: String
)

data class ProductionCountries(
    val iso31661: String,
    val name: String
)