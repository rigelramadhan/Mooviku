package com.rivibi.mooviku.core.di

import androidx.room.Room
import com.rivibi.mooviku.core.data.local.datasource.LocalDataSource
import com.rivibi.mooviku.core.data.local.room.database.MovieDao
import com.rivibi.mooviku.core.data.local.room.database.MovieDatabase
import com.rivibi.mooviku.core.data.remote.datasource.RemoteDataSource
import com.rivibi.mooviku.core.data.repository.MovieRepository
import com.rivibi.mooviku.core.domain.repository.IMovieRepository
import com.rivibi.mooviku.core.utils.AppExecutors
import org.koin.dsl.module

val databaseModule = module {

    fun getDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.getDao()
    }

    single {
        return@single Room.databaseBuilder(get(), MovieDatabase::class.java, "movie.db")
            .fallbackToDestructiveMigration().build()
    }

    single {
        getDao(get())
    }
}

val repositoryModule = module {

    single { RemoteDataSource(get()) }

    single { LocalDataSource(get()) }

    factory { AppExecutors() }

    single<IMovieRepository> {
        MovieRepository(get(), get(), get())
    }
}