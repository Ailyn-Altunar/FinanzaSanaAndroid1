package com.ailyn.finanzasana.core.hardware.biometric.data

import androidx.fragment.app.FragmentActivity
import com.ailyn.finanzasana.core.hardware.biometric.BiometricHelper
import com.ailyn.finanzasana.core.hardware.biometric.BiometricResult
import com.ailyn.finanzasana.core.hardware.biometric.domain.BiometricManager
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidBiometricManager @Inject constructor() : BiometricManager {

    override suspend fun autenticar(activity: FragmentActivity): Boolean =
        suspendCoroutine { cont ->
            BiometricHelper.authenticate(
                activity = activity,
                title = "Acceso Restringido",
                subtitle = "Usa tu huella para continuar",
                negativeButtonText = "Cancelar",
                onResult = { result ->
                    cont.resume(result is BiometricResult.Success)
                }
            )
        }
}
