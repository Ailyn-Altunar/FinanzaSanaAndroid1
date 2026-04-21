package com.ailyn.finanzasana.features.admin.usuarios.di

import com.ailyn.finanzasana.features.admin.usuarios.data.datasource.api.UsuariosApi
import com.ailyn.finanzasana.features.admin.usuarios.data.repositories.UsuariosRepositoryImpl
import com.ailyn.finanzasana.features.admin.usuarios.domain.repositories.UsuariosRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsuariosModule {

    @Binds
    @Singleton
    abstract fun bindUsuariosRepository(
        impl: UsuariosRepositoryImpl
    ): UsuariosRepository

    companion object {
        @Provides
        @Singleton
        fun provideUsuariosApi(retrofit: Retrofit): UsuariosApi {
            return retrofit.create(UsuariosApi::class.java)
        }
    }
}
