package com.duycomp.autoclicker.feature.overlay.controller

import android.content.Context
import android.view.WindowManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.accessibility.AcAccessibility
import com.duycomp.autoclicker.feature.overlay.target.ManagerTargets
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.model.TargetData
import com.duycomp.autoclicker.model.TimerSchedule
import com.duycomp.autoclicker.model.startTargetsPosition
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var configClick: ConfigClick = ConfigClick()
    var defaultTargetClick: TargetClick = TargetClick(
        position = startTargetsPosition,
        intervalClick = 100L,
        durationClick = 10L
    )

    init {
        viewModelScope.launch {
            userDataRepository.userData.collectLatest {
                configClick = configClick.copy(
                    nLoop = it.nLoop,
                    isInfinityLoop = it.isInfinityLoop,
                    timerSchedule = TimerSchedule(earlyClick = it.earlyTime),
                )

                defaultTargetClick = defaultTargetClick.copy(
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
        managerTargets.addTarget(context, windowManager, defaultTargetClick, configClick.targetsData)
    }

    fun onRemoveClick() {
        managerTargets.removeTarget(windowManager, configClick.targetsData)
    }

    fun onSettingClick() {
        TODO("Not yet implemented")
    }

    fun onClockClick() {
        TODO("Not yet implemented")
    }

    fun onFolderClick() {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        managerTargets.removeAllTargets(windowManager, configClick.targetsData)
        super.onCleared()
    }


}