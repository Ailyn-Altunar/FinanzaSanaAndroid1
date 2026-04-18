package com.ailyn.finanzasana.core.hardware.biometric.domain

import androidx.fragment.app.FragmentActivity

interface BiometricManager {
    suspend fun autenticar(activity: FragmentActivity): Boolean
}
