package com.duycomp.autoclicker.database.di

import android.content.Context
import androidx.room.Room
import com.duycomp.autoclicker.database.ClickerConfig
import com.duycomp.autoclicker.database.ClickerConfigDB
import com.duycomp.autoclicker.database.ClickerConfigDao
import com.duycomp.autoclicker.database.DB_NAME
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
    fun provideVideoInfoDb(@ApplicationContext applicationContext: Context): ClickerConfigDB {
        return Room.databaseBuilder(
            applicationContext,
            ClickerConfigDB::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideVideoInfoDao(db: ClickerConfigDB): ClickerConfigDao {
        return db.clickerConfigDao()
    }
}