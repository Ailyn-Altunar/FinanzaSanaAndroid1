package com.ailyn.finanzasana.features.home.deuda.di

import com.ailyn.finanzasana.features.home.deuda.data.datasource.api.DeudaApi
import com.ailyn.finanzasana.features.home.deuda.data.repositories.DeudaRepositoryImpl
import com.ailyn.finanzasana.features.home.deuda.domain.repositories.DeudaRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DeudaModule {

    @Binds
    @Singleton
    abstract fun bindDeudaRepository(
        deudaRepositoryImpl: DeudaRepositoryImpl
    ): DeudaRepository

    companion object {
        @Provides
        @Singleton
        fun provideDeudaApi(retrofit: Retrofit): DeudaApi =
            retrofit.create(DeudaApi::class.java)
    }
}
