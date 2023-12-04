package com.duycomp.autoclicker.database

import androidx.room.*
import com.duycomp.autoclicker.database.model.ConfigPreview
import kotlinx.coroutines.flow.Flow

@Dao
interface ClickerConfigDao {
    @Query("SELECT id, `order`, configName FROM configuration_clicker")
    fun getAllConfigName(): Flow<List<ConfigPreview>>

    @Query("SELECT * FROM configuration_clicker WHERE id = :configId")
    suspend fun getConfig(configId: Int): ClickerConfigEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clickerConfigEntity: ClickerConfigEntity): Long

    @Delete
    suspend fun delete(clickerConfigEntity: ClickerConfigEntity)

    @Query("DELETE FROM configuration_clicker WHERE id = :configId")
    suspend fun deleteById(configId: Int)

    @Update
    suspend fun update(clickerConfigEntity: ClickerConfigEntity)


}