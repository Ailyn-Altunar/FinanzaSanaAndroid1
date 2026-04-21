package com.ailyn.finanzasana.core.hardware.biometric.domain

import androidx.fragment.app.FragmentActivity

interface BiometricAuthenticator {
    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}
