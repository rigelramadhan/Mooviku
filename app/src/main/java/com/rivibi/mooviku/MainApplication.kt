package com.rivibi.mooviku

import android.app.Application
import com.rivibi.mooviku.core.di.databaseModule
import com.rivibi.mooviku.core.di.networkModule
import com.rivibi.mooviku.core.di.repositoryModule
import com.rivibi.mooviku.di.useCaseModule
import com.rivibi.mooviku.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}