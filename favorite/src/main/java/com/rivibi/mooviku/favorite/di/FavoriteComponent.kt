package com.rivibi.mooviku.favorite.di

import android.content.Context
import com.rivibi.mooviku.core.di.FavoriteModuleDependencies
import com.rivibi.mooviku.favorite.ui.FavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModuleDependencies::class]
)
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}