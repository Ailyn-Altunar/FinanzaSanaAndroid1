package com.ailyn.finanzasana.features.home.empresas.data.datasource.api

import com.ailyn.finanzasana.features.home.empresas.data.models.EmpresaDto


import retrofit2.http.GET

interface EmpresasApi {

    @GET("/empresas")
    suspend fun getEmpresas(): List<EmpresaDto>
}
