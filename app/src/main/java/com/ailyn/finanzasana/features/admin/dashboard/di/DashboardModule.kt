package com.ailyn.finanzasana.features.admin.dashboard.di

import com.ailyn.finanzasana.core.hardware.biometric.BiometricHelper
import com.ailyn.finanzasana.core.hardware.biometric.domain.BiometricAuthenticator
import com.ailyn.finanzasana.features.admin.dashboard.data.datasource.api.DashboardApi
import com.ailyn.finanzasana.features.admin.dashboard.data.repositories.DashboardRepositoryImpl
import com.ailyn.finanzasana.features.admin.dashboard.domain.repositories.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    @Singleton
    fun provideDashboardApi(retrofit: Retrofit): DashboardApi {
        return retrofit.create(DashboardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(repository: DashboardRepositoryImpl): DashboardRepository {
        return repository
    }
}
