package com.nullable.ymgalgame.ui.feature.foryou.module

import com.nullable.ymgalgame.ui.feature.foryou.repository.ForYouRepository
import com.nullable.ymgalgame.ui.feature.foryou.repository.ForYouRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ForYouModule {

    @Provides
    fun provideForYouRepository(): ForYouRepository {
        return ForYouRepositoryImpl()
    }

}
