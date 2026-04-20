package com.ailyn.finanzasana.core.hardware.biometric.domain

interface BiometricAuthenticator {
    fun isBiometricAvailable(): Boolean
    fun hasEnrolledBiometrics(): Boolean
    fun canAuthenticate(): Boolean
}
