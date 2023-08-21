package com.rivibi.mooviku.di

import com.rivibi.mooviku.core.domain.usecase.MovieInteractor
import com.rivibi.mooviku.core.domain.usecase.MovieUseCase
import com.rivibi.mooviku.ui.detail.DetailViewModel
import com.rivibi.mooviku.ui.main.MainViewModel
import com.rivibi.mooviku.ui.main.genre.GenreViewModel
import com.rivibi.mooviku.ui.main.home.HomeViewModel
import com.rivibi.mooviku.ui.movielist.MovieListViewModel
import com.rivibi.mooviku.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { MovieListViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { GenreViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class AppModule {
//
//    @Binds
//    @Singleton
//    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
//}