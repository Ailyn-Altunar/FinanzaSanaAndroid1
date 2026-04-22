package com.ailyn.finanzasana.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ailyn.finanzasana.core.database.entities.SolicitudEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {
    @Query("SELECT * FROM solicitudes")
    fun getAllSolicitudes(): Flow<List<SolicitudEntity>>

    @Query("SELECT * FROM solicitudes WHERE estado = :estado")
    fun getSolicitudesByEstado(estado: Int): Flow<List<SolicitudEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSolicitudes(solicitudes: List<SolicitudEntity>)

    @Query("DELETE FROM solicitudes WHERE estado = :estado")
    suspend fun deleteSolicitudesByEstado(estado: Int)

    @Query("DELETE FROM solicitudes")
    suspend fun deleteAllSolicitudes()
}
