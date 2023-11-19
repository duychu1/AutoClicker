package com.duycomp.autoclicker.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.duycomp.autoclicker.DarkThemeConfigProto
import com.duycomp.autoclicker.UserPreferences
import com.duycomp.autoclicker.common.network.Dispatcher
import com.duycomp.autoclicker.common.network.DownloaderDispatchers
import com.duycomp.autoclicker.common.network.di.ApplicationScope
import com.duycomp.autoclicker.copy
import com.duycomp.autoclicker.model.UserData
import com.duycomp.autoclicker.model.DarkThemeConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData = userPreferences.data
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
            )
        }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicColor = useDynamicColor
            }
        }
    }

    suspend fun setEarlyTime(value: Long) {
        userPreferences.updateData {
            it.copy {
                this.earlyTime = value
            }
        }
    }

    suspend fun setDurationClick(value: Long) {
        userPreferences.updateData {
            it.copy {
                this.durationClick = value
            }
        }
    }

    suspend fun setIntervalClick(value: Long) {
        userPreferences.updateData {
            it.copy {
                this.intervalClick = value
            }
        }
    }

    suspend fun setInfinityLoop(value: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.isInfinityLoop = value
            }
        }
    }

    suspend fun setLoop(value: Int) {
        userPreferences.updateData {
            it.copy {
                this.nLoop = value
            }
        }
    }


}