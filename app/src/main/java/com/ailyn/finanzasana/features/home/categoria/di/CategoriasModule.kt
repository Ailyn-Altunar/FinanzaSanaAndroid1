package com.ailyn.finanzasana.features.home.categoria.di

import com.ailyn.finanzasana.features.home.categoria.data.datasource.api.CategoriasApi
import com.ailyn.finanzasana.features.home.categoria.data.repositories.CategoriasRepositoryImpl
import com.ailyn.finanzasana.features.home.categoria.domain.repositories.CategoriasRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriasModule {

    @Binds
    @Singleton
    abstract fun bindCategoriasRepository(
        impl: CategoriasRepositoryImpl
    ): CategoriasRepository

    companion object {
        @Provides
        @Singleton
        fun provideCategoriasApi(retrofit: Retrofit): CategoriasApi =
            retrofit.create(CategoriasApi::class.java)
    }
}
