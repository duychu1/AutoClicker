package com.duycomp.autoclicker.data

import com.duycomp.autoclicker.database.ClickerConfigEntity
import com.duycomp.autoclicker.database.ClickerConfigDao
import com.duycomp.autoclicker.model.ConfigClick
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClickerConfigDatabaseRepositoryImpl @Inject constructor(
    private val clickerConfigDao: ClickerConfigDao
) {
    fun observeAllConfigName(): Flow<List<String>> =
        clickerConfigDao.getAllConfigName()

    fun getConfig(configName: String): Flow<ClickerConfigEntity> =
        clickerConfigDao.getConfig(configName)

     suspend fun insert(configClick: ConfigClick) {
        clickerConfigDao.insert(configClick.asEntities())
    }

    suspend fun delete(configClick: ConfigClick) {
        clickerConfigDao.delete(configClick.asEntities())
    }

    suspend fun update(configClick: ConfigClick) {
        clickerConfigDao.update(configClick.asEntities())
    }

}