package com.rivibi.mooviku.core.domain.model

data class Movie(
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val genreIds: List<Int>,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val id: Int,
    val adult: Boolean,
    val voteCount: Int,
    val category: String,
    val favorite: Boolean,
)
