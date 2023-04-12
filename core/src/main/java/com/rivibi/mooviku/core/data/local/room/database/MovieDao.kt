package com.rivibi.mooviku.core.data.local.room.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM ")
    fun getNowPlaying()
}