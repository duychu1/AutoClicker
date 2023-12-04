package com.duycomp.autoclicker.data

import android.util.Log
import com.duycomp.autoclicker.database.ClickerConfigDao
import com.duycomp.autoclicker.database.ClickerConfigEntity
import com.duycomp.autoclicker.database.model.ConfigPreview
import com.duycomp.autoclicker.model.ConfigClick
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClickerConfigDatabaseRepositoryImpl @Inject constructor(
    private val clickerConfigDao: ClickerConfigDao
) {
    fun observeAllConfigName(): Flow<List<ConfigPreview>> =
        clickerConfigDao.getAllConfigName()

    suspend fun getConfig(configPreview: ConfigPreview): ClickerConfigEntity =
        clickerConfigDao.getConfig(configPreview.id)

    suspend fun insert(configClick: ConfigClick): Long {
        if (configClick.id == -1) configClick.id = 0
        Log.d("TAG", "insert: ${configClick.asEntities()}")
        return clickerConfigDao.insert(configClick.asEntities())
    }

    suspend fun delete(configPreview: ConfigPreview) {
        clickerConfigDao.deleteById(configPreview.id)
    }

    suspend fun update(configClick: ConfigClick) {
        if (configClick.id == -1) return
        clickerConfigDao.update(configClick.asEntities())
    }

}

