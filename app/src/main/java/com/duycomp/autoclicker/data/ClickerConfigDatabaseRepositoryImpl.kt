package com.duycomp.autoclicker.data

import com.duycomp.autoclicker.database.ClickerConfig
import com.duycomp.autoclicker.database.ClickerConfigDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClickerConfigDatabaseRepositoryImpl @Inject constructor(
    private val clickerConfigDao: ClickerConfigDao
) {
    fun observeAllConfigName(): Flow<List<String>> =
        clickerConfigDao.getAllConfigName()

    fun getConfig(configName: String): Flow<ClickerConfig> =
        clickerConfigDao.getConfig(configName)

     suspend fun insert(clickerConfig: ClickerConfig) {
        clickerConfigDao.insert(clickerConfig)
    }

    suspend fun delete(clickerConfig: ClickerConfig) {
        clickerConfigDao.delete(clickerConfig)
    }

    suspend fun update(clickerConfig: ClickerConfig) {
        clickerConfigDao.update(clickerConfig)
    }

}