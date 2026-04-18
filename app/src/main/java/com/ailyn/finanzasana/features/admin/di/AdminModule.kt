package com.ailyn.finanzasana.features.admin.di

import com.ailyn.finanzasana.features.admin.data.datasource.AdminDataSource
import com.ailyn.finanzasana.features.admin.data.datasource.AdminDataSourceImpl
import com.ailyn.finanzasana.features.admin.data.repository.AdminRepositoryImpl
import com.ailyn.finanzasana.features.admin.domain.repository.AdminRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdminModule {

    @Provides
    @Singleton
    fun provideAdminDataSource(
        httpClient: HttpClient
    ): AdminDataSource {
        return AdminDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideAdminRepository(
        dataSource: AdminDataSource
    ): AdminRepository {
        return AdminRepositoryImpl(dataSource)
    }
}