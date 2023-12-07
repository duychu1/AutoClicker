package com.duycomp.autoclicker.feature.overlay.folder_config

import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.data.ClickerConfigDatabaseRepositoryImpl
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.database.model.ConfigPreview
import com.duycomp.autoclicker.feature.overlay.target.ManagerTargets
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.model.asModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfigSavedDialogViewModel(
    private val composeView: ComposeView,
    private val windowManager: WindowManager,
    private val managerTargets: ManagerTargets,
    private val userDataRepository: UserDataRepositoryImpl,
    private val configDatabaseRepository: ClickerConfigDatabaseRepositoryImpl,
//    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined),
): ViewModel() {

    val configState: StateFlow<ConfigUiState> =
        configDatabaseRepository.observeAllConfigName().map {
            ConfigUiState.Success(
                configsPreview = it
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConfigUiState.Loading,
        )

    fun onDeleteConfig(configPreview: ConfigPreview) = viewModelScope.launch {
        configDatabaseRepository.delete(configPreview)
    }
    @OptIn(ExperimentalComposeUiApi::class)
    fun onOpenConfig(
        context: Context,
        configPreview: ConfigPreview,
        configClick: ConfigClick,
        onConfigChange: (ConfigClick) -> Unit
    ) {
        viewModelScope.launch {
            managerTargets.removeAllTargets(windowManager, configClick.targetsData)

            val config = withContext(Dispatchers.IO) {
                configDatabaseRepository.getConfig(configPreview)
            }
            onConfigChange(config.asModel(context, windowManager))

            onDismiss()
        }
    }
    fun onCreateNewConfig(configClick: ConfigClick, onConfigChange: (ConfigClick) -> Unit) = viewModelScope.launch {
        managerTargets.removeAllTargets(windowManager, configClick.targetsData)
        onConfigChange(ConfigClick())
        onDismiss()
    }
    fun onDismiss()  {
        try {
            Log.d(TAG, "onDismiss: remove ConfigSavedDialog")
            windowManager.removeView(composeView)
        } catch (e: Exception) {
            TODO("Not yet implemented")
        }
    }

}

sealed interface ConfigUiState {
    data object Loading : ConfigUiState

    data class Success(
        val configsPreview: List<ConfigPreview>,
    ) : ConfigUiState

}

const val TAG = "ConfigSavedDialogViewModel"