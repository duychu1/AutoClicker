package com.duycomp.autoclicker.data

import com.duycomp.autoclicker.datastore.PreferencesDataSource
import com.duycomp.autoclicker.model.DarkThemeConfig
import com.duycomp.autoclicker.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) {
    val userData: Flow<UserData>
        get() = preferencesDataSource.userData

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        preferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        preferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    suspend fun setDurationClick(value: Long) {
        preferencesDataSource.setDurationClick(value)
    }

    suspend fun setIntervalClick(value: Long) {
        preferencesDataSource.setIntervalClick(value)
    }

    suspend fun setEarlyTime(value: Long) {
        preferencesDataSource.setEarlyTime(value)
    }

    suspend fun setInfinityLoop(value: Boolean) {
        preferencesDataSource.setInfinityLoop(value)
    }

    suspend fun setLoop(value: Int) {
        preferencesDataSource.setLoop(value)
    }
}