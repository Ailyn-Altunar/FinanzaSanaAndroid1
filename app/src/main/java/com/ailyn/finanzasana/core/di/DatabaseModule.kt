package com.ailyn.finanzasana.core.di

import android.content.Context
import androidx.room.Room
import com.ailyn.finanzasana.core.database.FinanzaSanaDatabase
import com.ailyn.finanzasana.core.database.dao.SolicitudDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FinanzaSanaDatabase {
        return Room.databaseBuilder(
            context,
            FinanzaSanaDatabase::class.java,
            "finanzasana_db"
        ).build()
    }

    @Provides
    fun provideSolicitudDao(db: FinanzaSanaDatabase): SolicitudDao {
        return db.solicitudDao()
    }
}
