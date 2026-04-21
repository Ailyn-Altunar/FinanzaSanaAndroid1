package com.ailyn.finanzasana.features.home.empresas.di


import com.ailyn.finanzasana.features.home.empresas.data.datasource.api.EmpresasApi
import com.ailyn.finanzasana.features.home.empresas.data.repositories.EmpresasRepositoryImpl
import com.ailyn.finanzasana.features.home.empresas.domain.repositories.EmpresasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmpresasModule {

    @Provides
    @Singleton
    fun provideEmpresasApi(retrofit: Retrofit): EmpresasApi =
        retrofit.create(EmpresasApi::class.java)

    @Provides
    @Singleton
    fun provideEmpresasRepository(api: EmpresasApi): EmpresasRepository =
        EmpresasRepositoryImpl(api)
}
