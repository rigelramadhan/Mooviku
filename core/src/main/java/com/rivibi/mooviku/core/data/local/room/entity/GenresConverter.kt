package com.rivibi.mooviku.core.data.local.room.entity

import androidx.room.TypeConverter

class GenresConverter {
    @TypeConverter
    fun storedStringToGenres(value: String): GenreList {
        val genres = value.split("\\s*,\\s*").map { it.toInt() }
        return GenreList(genres)
    }

    @TypeConverter
    fun genresToStoredString(value: GenreList): String {
        var str = ""
        value.genreIds.forEach {
            str += "$it,"
        }

        return str
    }
}