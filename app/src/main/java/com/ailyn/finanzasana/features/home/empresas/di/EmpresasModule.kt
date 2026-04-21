package com.ailyn.finanzasana.features.home.empresas.di


import com.ailyn.finanzasana.features.home.empresas.data.datasource.api.EmpresasApi
import com.ailyn.finanzasana.features.home.empresas.data.repositories.EmpresasRepositoryImpl
import com.ailyn.finanzasana.features.home.empresas.domain.repositories.EmpresasRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EmpresasModule {

    @Binds
    @Singleton
    abstract fun bindEmpresasRepository(
        impl: EmpresasRepositoryImpl
    ): EmpresasRepository

    companion object {
        @Provides
        @Singleton
        fun provideEmpresasApi(retrofit: Retrofit): EmpresasApi =
            retrofit.create(EmpresasApi::class.java)
    }
}
