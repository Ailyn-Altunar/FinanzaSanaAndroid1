package com.ailyn.finanzasana.core.hardware.di

import com.ailyn.finanzasana.core.hardware.location.domain.LocationManager
import com.ailyn.finanzasana.core.hardware.location.data.AndroidLocationManager

import com.ailyn.finanzasana.core.hardware.camera.domain.CameraManager
import com.ailyn.finanzasana.core.hardware.camera.data.AndroidCameraManager

import com.ailyn.finanzasana.core.hardware.biometric.domain.BiometricAuthenticator
import com.ailyn.finanzasana.core.hardware.biometric.data.AndroidBiometricAuthenticator

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {

    @Binds
    @Singleton
    abstract fun bindLocationManager(
        impl: AndroidLocationManager
    ): LocationManager

    @Binds
    @Singleton
    abstract fun bindCameraManager(
        impl: AndroidCameraManager
    ): CameraManager

    @Binds
    @Singleton
    abstract fun bindBiometricAuthenticator(
        impl: AndroidBiometricAuthenticator
    ): BiometricAuthenticator
}
