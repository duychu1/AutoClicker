package com.duycomp.autoclicker.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.duycomp.autoclicker.DarkThemeConfigProto
import com.duycomp.autoclicker.TAG
import com.duycomp.autoclicker.UserPreferences
import com.duycomp.autoclicker.model.DarkThemeConfig
import com.duycomp.autoclicker.model.UserData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData = userPreferences.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
            } else {
                throw exception
            }
        }
        .map {
            UserData (
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                    ->
                        DarkThemeConfig.FOLLOW_SYSTEM
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                        DarkThemeConfig.LIGHT
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                },
                useDynamicColor = it.useDynamicColor,
                intervalClick = it.intervalClick,
                durationClick = it.durationClick,
                nLoop = it.nLoop,
                isInfinityLoop = it.isInfinityLoop,
                earlyTime = it.earlyTime,
                isManualZoneOffset = it.isManualZoneOffset,
                isManualClockOffset = it.isManualClockOffset,
                clockOffset = it.clockOffset,
                zoneOffset = it.zoneOffset,
            )
        }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            val value = when (darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM ->
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
            }
            it.toBuilder().setDarkThemeConfig(value).build()
        }
    }

    suspend fun setDynamicColorPreference(value: Boolean) {
        userPreferences.updateData {
            it.toBuilder().setUseDynamicColor(value).build()
        }
    }

    suspend fun setEarlyTime(value: Long) {
        userPreferences.updateData {
            it.toBuilder().setEarlyTime(value).build()
        }
    }

    suspend fun setDurationClick(value: Long) {
        userPreferences.updateData {
            it.toBuilder().setDurationClick(value).build()
        }
    }

    suspend fun setIntervalClick(value: Long) {
        userPreferences.updateData {
            it.toBuilder().setIntervalClick(value).build()
        }
    }

    suspend fun setInfinityLoop(value: Boolean) {
        userPreferences.updateData {
            it.toBuilder().setIsInfinityLoop(value).build()
        }
    }

    suspend fun setLoop(value: Int) {
        userPreferences.updateData {
            it.toBuilder().setNLoop(value).build()
        }
    }

    suspend fun setManualZoneOffset(value: Boolean) {
        userPreferences.updateData {
            it.toBuilder().setIsManualZoneOffset(value).build()
        }
    }

    suspend fun setManualClockOffset(value: Boolean) {
        userPreferences.updateData {
            it.toBuilder().setIsManualClockOffset(value).build()
        }
    }

    suspend fun setClockOffset(value: Long) {
        userPreferences.updateData {
            it.toBuilder().setClockOffset(value).build()
        }
    }

    suspend fun setZoneOffset(value: Long) {
        userPreferences.updateData {
            it.toBuilder().setZoneOffset(value).build()
        }
    }


}