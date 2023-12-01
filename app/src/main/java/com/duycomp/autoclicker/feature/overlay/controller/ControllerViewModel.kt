package com.duycomp.autoclicker.feature.overlay.controller

import android.content.Context
import android.view.WindowManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.accessibility.AcAccessibility
import com.duycomp.autoclicker.feature.overlay.setting.settingDialogView
import com.duycomp.autoclicker.feature.overlay.target.ManagerTargets
import com.duycomp.autoclicker.feature.overlay.target.dialogLayout
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.model.TimerSchedule
import com.duycomp.autoclicker.model.ViewLayout
import com.duycomp.autoclicker.model.startTargetsPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
//    private val userDataRepository: UserDataRepositoryImpl
): ViewModel() {
    private val windowManager: WindowManager = AcAccessibility.windowManager!!
    private val managerTargets: ManagerTargets = ManagerTargets()
    private val userDataRepository = AcAccessibility.acUserDataRepository!!
    private val configDatabaseRepository = AcAccessibility.acConfigDatabaseRepository!!

    private val _config = MutableStateFlow(ConfigClick())
    private val _targetClick = MutableStateFlow(
        TargetClick(
            position = startTargetsPosition,
            intervalClick = 100L,
            durationClick = 10L
        )
    )

    val config = _config.asStateFlow()

//    val con = userDataRepository.userData.map {
//        _config.value = _config.value.copy(
//            nLoop = it.nLoop,
//            isInfinityLoop = it.isInfinityLoop,
//            timerSchedule = TimerSchedule(earlyClick = it.earlyTime),
//        )
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = _config.value
//    )

    var configClick: ConfigClick = ConfigClick()

    var defaultTargetClick: TargetClick = TargetClick(
        position = startTargetsPosition,
        intervalClick = 100L,
        durationClick = 10L
    )

    init {
        viewModelScope.launch {
            userDataRepository.userData.collectLatest {
                _config.value = _config.value.copy(
                    nLoop = it.nLoop,
                    isInfinityLoop = it.isInfinityLoop,
                    timerSchedule = TimerSchedule(earlyClick = it.earlyTime),
                )

                _targetClick.value = _targetClick.value.copy(
                    intervalClick = it.intervalClick,
                    durationClick = it.durationClick
                )
            }
        }
    }

    var isPlaying by mutableStateOf(false)
        private set

    fun onPlayClick(value: Boolean) {
        isPlaying = value
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onAddClick(context: Context) {
        managerTargets.addTarget(context, windowManager, _targetClick.value, _config.value.targetsData)
    }

    fun onRemoveClick() {
        managerTargets.removeTarget(windowManager, _config.value.targetsData)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onSettingClick(context: Context) {
        ViewLayout(
            settingDialogView(
                context,
                windowManager,
                _config,
                userDataRepository,
                configDatabaseRepository,
            ),
            dialogLayout()
        ).addViewToWindowManager(windowManager)
    }

    fun onClockClick() {
        TODO("Not yet implemented")
    }

    fun onFolderClick() {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        managerTargets.removeAllTargets(windowManager, _config.value.targetsData)
        super.onCleared()
    }


}