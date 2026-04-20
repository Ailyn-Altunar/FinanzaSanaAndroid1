package com.ailyn.finanzasana.core.hardware.camera.domain

import android.graphics.Bitmap
import com.ailyn.finanzasana.core.hardware.camera.domain.model.FotoModel

interface CameraManager {
    fun tienePermisoCamara(): Boolean
    fun bitmapToBase64(bitmap: Bitmap): FotoModel
    fun base64ToBitmap(base64: String): Bitmap?
}
