package com.ailyn.finanzasana.features.auth.login.data.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("contrasena") val contrasena: String
)
