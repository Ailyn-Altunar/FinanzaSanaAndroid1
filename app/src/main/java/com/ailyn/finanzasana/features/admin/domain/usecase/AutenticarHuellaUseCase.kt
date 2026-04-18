package com.ailyn.finanzasana.features.admin.domain.usecase

import androidx.fragment.app.FragmentActivity
import com.ailyn.finanzasana.core.hardware.biometric.domain.BiometricManager
import javax.inject.Inject

class AutenticarHuellaUseCase @Inject constructor(
    private val biometricManager: BiometricManager
) {
    suspend operator fun invoke(activity: FragmentActivity): Boolean {
        return biometricManager.autenticar(activity)
    }
}
