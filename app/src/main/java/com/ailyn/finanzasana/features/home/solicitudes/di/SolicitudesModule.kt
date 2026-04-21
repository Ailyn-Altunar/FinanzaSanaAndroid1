package com.ailyn.finanzasana.features.home.solicitudes.di

import com.ailyn.finanzasana.features.home.solicitudes.data.datasource.api.SolicitudApi
import com.ailyn.finanzasana.features.home.solicitudes.data.repositories.SolicitudRepositoryImpl
import com.ailyn.finanzasana.features.home.solicitudes.domain.repositories.SolicitudRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SolicitudesModule {

    @Binds
    @Singleton
    abstract fun bindSolicitudRepository(
        solicitudRepositoryImpl: SolicitudRepositoryImpl
    ): SolicitudRepository

    companion object {
        @Provides
        @Singleton
        fun provideSolicitudApi(retrofit: Retrofit): SolicitudApi =
            retrofit.create(SolicitudApi::class.java)
    }
}
