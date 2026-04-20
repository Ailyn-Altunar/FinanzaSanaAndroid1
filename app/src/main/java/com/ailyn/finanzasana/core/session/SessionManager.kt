package com.ailyn.finanzasana.core.session

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val prefs = context.getSharedPreferences("finanzasana_session", Context.MODE_PRIVATE)

    fun saveSession(token: String, rol: Int, idUsuario: Int) {
        prefs.edit()
            .putString("jwt", token)
            .putInt("rol", rol)
            .putInt("id_usuario", idUsuario)
            .apply()
    }

    fun getToken(): String? = prefs.getString("jwt", null)

    fun getRol(): Int = prefs.getInt("rol", -1)

    fun getUserId(): Int = prefs.getInt("id_usuario", -1)

    fun isLoggedIn(): Boolean =
        getToken() != null && getUserId() != -1

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
