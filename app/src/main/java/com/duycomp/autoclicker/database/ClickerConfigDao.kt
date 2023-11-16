package com.duycomp.autoclicker.database

import androidx.room.*
import com.duycomp.autoclicker.database.model.TimerSchedule

@Dao
interface ClickerConfigDao {
    @Query("SELECT configName FROM configuration_clicker")
    suspend fun getAllConfigName(): List<String>

    @Query("SELECT * FROM configuration_clicker WHERE configName = :configName")
    suspend fun getConfig(configName: String): ClickerConfig

    @Insert
    suspend fun insertConfig(clickerConfig: ClickerConfig)

    @Delete
    suspend fun deleteConfig(clickerConfig: ClickerConfig)

    @Update
    suspend fun updateConfig(clickerConfig: ClickerConfig)


}