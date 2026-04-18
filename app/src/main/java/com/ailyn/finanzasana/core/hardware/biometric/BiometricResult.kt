package com.ailyn.finanzasana.core.hardware.biometric

sealed class BiometricResult {
    object Success : BiometricResult()
    object Failed : BiometricResult()
    data class Error(val message: String) : BiometricResult()

    object NoHardware : BiometricResult()
    object HardwareUnavailable : BiometricResult()
    object NoEnrolled : BiometricResult()

    object Unknown : BiometricResult()
}
