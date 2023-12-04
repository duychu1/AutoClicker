package com.duycomp.autoclicker.feature.overlay.setting

import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.data.ClickerConfigDatabaseRepositoryImpl
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.model.TimerSchedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//class SettingDialogViewModel(
//    private val _configClick: MutableStateFlow<ConfigClick>,
//    val windowManager: WindowManager,
//    val composeView: ComposeView,
//    private val userDataRepository: UserDataRepositoryImpl,
//    private val configDatabaseRepository: ClickerConfigDatabaseRepositoryImpl
//): ViewModel() {
//    val configClick = _configClick.asStateFlow()
//
//    fun onConfigNameChange(value: String) = viewModelScope.launch {
//        _configClick.value = _configClick.value.copy(configName = value)
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onTimerScheduleChange(timerSchedule: TimerSchedule) = viewModelScope.launch {
//        _configClick.value = _configClick.value.copy(timerSchedule = timerSchedule)
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onLoopChange(value: Int) = viewModelScope.launch {
//        _configClick.value = _configClick.value.copy(nLoop = value)
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onInfinityLoopChange(value: Boolean) = viewModelScope.launch {
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onDismiss() {
//        try {
//            windowManager.removeView(composeView)
//        } catch (e: Exception) {
//            TODO("Not yet implemented")
//        }
//    }
//
//
//}