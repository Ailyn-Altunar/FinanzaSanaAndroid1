package com.ailyn.finanzasana.core.hardware.location.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.ailyn.finanzasana.core.hardware.location.domain.LocationManager
import com.ailyn.finanzasana.core.hardware.location.domain.model.UbicacionModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidLocationManager @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationManager {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    override fun tienePermisoUbicacion(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    @Suppress("MissingPermission")
    override suspend fun obtenerUbicacion(): UbicacionModel? {
        if (!tienePermisoUbicacion()) return null

        return suspendCancellableCoroutine { cont ->
            val token = CancellationTokenSource()

            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                token.token
            ).addOnSuccessListener { location ->
                if (location == null) {
                    cont.resume(null)
                } else {
                    val direccion = obtenerDireccion(location.latitude, location.longitude)
                    cont.resume(
                        UbicacionModel(
                            latitud = location.latitude,
                            longitud = location.longitude,
                            direccion = direccion
                        )
                    )
                }
            }.addOnFailureListener {
                cont.resume(null)
            }
        }
    }

    private fun obtenerDireccion(lat: Double, lon: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale("es", "MX"))
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            addresses?.firstOrNull()?.getAddressLine(0)
        } catch (e: Exception) {
            null
        }
    }
}
