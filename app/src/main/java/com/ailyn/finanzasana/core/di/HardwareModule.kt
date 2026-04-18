package com.ailyn.finanzasana.core.di

import androidx.fragment.app.FragmentActivity
import com.ailyn.finanzasana.core.hardware.biometric.data.AndroidBiometricManager
import com.ailyn.finanzasana.core.hardware.biometric.domain.BiometricManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HardwareModule {

    @Binds
    @ViewModelScoped
    abstract fun bindBiometricManager(
        impl: AndroidBiometricManager
    ): BiometricManager
}

@Module
@InstallIn(ViewModelComponent::class)
object HardwareProvidesModule {

    @Provides
    @ViewModelScoped
    fun provideFragmentActivity(): FragmentActivity {
        throw IllegalStateException(
            "FragmentActivity must be provided by the screen using @AndroidEntryPoint"
        )
    }
}
