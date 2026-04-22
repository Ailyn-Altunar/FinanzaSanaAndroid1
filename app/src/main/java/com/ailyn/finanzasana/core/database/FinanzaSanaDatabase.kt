package com.ailyn.finanzasana.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ailyn.finanzasana.core.database.dao.SolicitudDao
import com.ailyn.finanzasana.core.database.entities.SolicitudEntity

@Database(
    entities = [
        SolicitudEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FinanzaSanaDatabase : RoomDatabase() {
    abstract fun solicitudDao(): SolicitudDao
}
