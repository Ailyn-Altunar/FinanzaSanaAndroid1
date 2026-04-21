package com.ailyn.finanzasana.features.admin.gestion_prestamos.di

import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.datasource.api.PrestamosAdminApi
import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.repositories.PrestamosAdminRepositoryImpl
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.repositories.PrestamosAdminRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GestionPrestamosModule {

    @Binds
    @Singleton
    abstract fun bindPrestamosAdminRepository(
        impl: PrestamosAdminRepositoryImpl
    ): PrestamosAdminRepository

    companion object {
        @Provides
        @Singleton
        fun providePrestamosAdminApi(retrofit: Retrofit): PrestamosAdminApi =
            retrofit.create(PrestamosAdminApi::class.java)
    }
}
