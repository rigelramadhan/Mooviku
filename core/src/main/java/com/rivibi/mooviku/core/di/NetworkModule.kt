package com.rivibi.mooviku.core.di

import com.rivibi.mooviku.core.data.remote.network.ApiConfig
import org.koin.dsl.module

val networkModule = module {

    single {
        ApiConfig.getApiService()
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideApiService(): ApiService {
//        return ApiConfig.getApiService()
//    }
//}