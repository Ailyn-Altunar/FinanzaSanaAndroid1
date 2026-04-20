package com.ailyn.finanzasana.core.hardware.biometric.data

import android.content.Context
import androidx.biometric.BiometricManager
import com.ailyn.finanzasana.core.hardware.biometric.domain.BiometricAuthenticator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidBiometricAuthenticator @Inject constructor(
    @ApplicationContext private val context: Context
) : BiometricAuthenticator {

    private val biometricManager = BiometricManager.from(context)

    override fun isBiometricAvailable(): Boolean {
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) != BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
    }

    override fun hasEnrolledBiometrics(): Boolean {
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) != BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
    }

    override fun canAuthenticate(): Boolean {
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }
}
