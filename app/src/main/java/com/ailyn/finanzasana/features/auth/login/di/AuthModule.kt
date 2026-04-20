package com.ailyn.finanzasana.features.auth.login.di

import com.ailyn.finanzasana.features.auth.login.data.datasource.api.AuthApi
import com.ailyn.finanzasana.features.auth.login.data.repositories.AuthRepositoryImpl
import com.ailyn.finanzasana.features.auth.login.domain.repositories.AuthRepository
import com.ailyn.finanzasana.features.auth.login.domain.usecases.LoginUseCase
import com.ailyn.finanzasana.features.auth.login.domain.usecases.RegisterUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    companion object {

        @Provides
        @Singleton
        fun provideAuthApi(retrofit: Retrofit): AuthApi =
            retrofit.create(AuthApi::class.java)

        @Provides
        @Singleton
        fun provideLoginUseCase(repository: AuthRepository): LoginUseCase =
            LoginUseCase(repository)

        @Provides
        @Singleton
        fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase =
            RegisterUseCase(repository)
    }
}
