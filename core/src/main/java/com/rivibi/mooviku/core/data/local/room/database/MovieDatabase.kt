package com.rivibi.mooviku.core.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivibi.mooviku.core.data.local.room.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getDao(): MovieDao
}