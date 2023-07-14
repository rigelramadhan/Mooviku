package com.rivibi.mooviku.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortQuery {

    fun getSortedMovieByFilter(
        sortFilter: SortFilter,
        genreId: Int = -1
    ): SimpleSQLiteQuery {
        val query = StringBuilder("SELECT * FROM movies")

        if (genreId > -1) query.append(" WHERE genreIds LIKE '%$genreId,%'")

        when (sortFilter) {
            SortFilter.Popularity -> query.append(" ORDER BY popularity DESC")

            SortFilter.Latest -> query.append(" ORDER BY releaseDate ASC")

            SortFilter.TopRated -> query.append(" ORDER BY voteAverage DESC")

            else -> {}
        }

        return SimpleSQLiteQuery(query.toString())
    }
}