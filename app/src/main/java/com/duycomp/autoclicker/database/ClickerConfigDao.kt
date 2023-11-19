package com.duycomp.autoclicker.database

import androidx.room.*
import com.duycomp.autoclicker.database.model.TimerSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ClickerConfigDao {
    @Query("SELECT configName FROM configuration_clicker")
    fun getAllConfigName(): Flow<List<String>>

    @Query("SELECT * FROM configuration_clicker WHERE configName = :configName")
    fun getConfig(configName: String): Flow<ClickerConfig>

    @Insert
    suspend fun insert(clickerConfig: ClickerConfig)

    @Delete
    suspend fun delete(clickerConfig: ClickerConfig)

    @Update
    suspend fun update(clickerConfig: ClickerConfig)


}