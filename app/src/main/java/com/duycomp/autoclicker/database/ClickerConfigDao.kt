package com.duycomp.autoclicker.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClickerConfigDao {
    @Query("SELECT configName FROM configuration_clicker")
    fun getAllConfigName(): Flow<List<String>>

    @Query("SELECT * FROM configuration_clicker WHERE configName = :configName")
    fun getConfig(configName: String): Flow<ClickerConfigEntity>

    @Insert
    suspend fun insert(clickerConfigEntity: ClickerConfigEntity)

    @Delete
    suspend fun delete(clickerConfigEntity: ClickerConfigEntity)

    @Update
    suspend fun update(clickerConfigEntity: ClickerConfigEntity)


}