package com.rivibi.mooviku.core.data.local.room.entity

import android.os.Build
import androidx.room.TypeConverter

class GenresConverter {
    @TypeConverter
    fun storedStringToGenres(value: String): GenreList {
        val genres = value.split(",").filter { it.isNotEmpty() }.map { it.toInt() }
        return GenreList(genres)
    }

    @TypeConverter
    fun genresToStoredString(value: GenreList): String {
        var str = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            value.genreIds.forEach {
                str += "$it,"
            }
        }

        return str
    }
}