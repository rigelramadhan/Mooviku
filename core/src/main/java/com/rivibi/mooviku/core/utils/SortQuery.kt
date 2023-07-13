package com.rivibi.mooviku.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortQuery {

    fun getSortedMovieByFilter(
        sortFilter: SortFilter,
        genreId: Int = -1
    ): SimpleSQLiteQuery {
        val query = StringBuilder("SELECT * FROM movies")

        if (genreId > -1) query.append(" WHERE genre LIKE '%$genreId,%'")

        when (sortFilter) {
            SortFilter.Popularity -> query.append(" SORT BY popularity DSC")

            SortFilter.Latest -> query.append(" SORT BY release_date ASC")

            SortFilter.TopRated -> query.append(" SORT BY vote_average DSC")

            else -> {}
        }

        return SimpleSQLiteQuery(query.toString())
    }
}