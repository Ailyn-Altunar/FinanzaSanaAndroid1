package com.ailyn.finanzasana.features.home.deuda.data.datasource.api

import com.ailyn.finanzasana.features.home.deuda.data.models.AbonoRequest
import com.ailyn.finanzasana.features.home.deuda.data.models.AbonoResponse
import com.ailyn.finanzasana.features.home.deuda.data.models.DeudaResponse
import com.ailyn.finanzasana.features.home.deuda.data.models.ResumenDeudaResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DeudaApi {
    @GET("/deudas")
    suspend fun getDeudas(): List<DeudaResponse>

    @GET("/deudas/total-adeudado")
    suspend fun getResumenDeuda(): ResumenDeudaResponse

    @GET("/abonos/{idDeuda}")
    suspend fun getAbonosByDeuda(@Path("idDeuda") idDeuda: Int): List<AbonoResponse>

    @POST("/abonos/{idDeuda}")
    suspend fun registrarAbono(
        @Path("idDeuda") idDeuda: Int,
        @Body request: AbonoRequest
    ): AbonoResponse

    @POST("/deudas/{idDeuda}/liquidar")
    suspend fun liquidarDeuda(@Path("idDeuda") idDeuda: Int): DeudaResponse
}
