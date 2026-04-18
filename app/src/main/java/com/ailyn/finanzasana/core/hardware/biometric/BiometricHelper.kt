package com.ailyn.finanzasana.core.hardware.biometric

import android.content.Context
import androidx.biometric.BiometricManager as AndroidBiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricHelper {

    fun canAuthenticate(context: Context): BiometricResult {
        val biometricManager = AndroidBiometricManager.from(context)

        return when (biometricManager.canAuthenticate(
            AndroidBiometricManager.Authenticators.BIOMETRIC_STRONG
                    or AndroidBiometricManager.Authenticators.BIOMETRIC_WEAK
        )) {
            AndroidBiometricManager.BIOMETRIC_SUCCESS -> BiometricResult.Success
            AndroidBiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricResult.NoHardware
            AndroidBiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricResult.HardwareUnavailable
            AndroidBiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricResult.NoEnrolled
            else -> BiometricResult.Unknown
        }
    }

    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        negativeButtonText: String,
        onResult: (BiometricResult) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText(negativeButtonText)
            .setAllowedAuthenticators(
                AndroidBiometricManager.Authenticators.BIOMETRIC_STRONG
                        or AndroidBiometricManager.Authenticators.BIOMETRIC_WEAK
            )
            .build()

        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onResult(BiometricResult.Success)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onResult(BiometricResult.Error(errString.toString()))
                }

                override fun onAuthenticationFailed() {
                    onResult(BiometricResult.Failed)
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }
}
