package com.duycomp.autoclicker.feature.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.common.result.Result
import com.duycomp.autoclicker.data.ClockOffsetRepository
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.feature.accessibility.AcAccessibility
import com.duycomp.autoclicker.feature.overlay.clock.clockOffset
import com.duycomp.autoclicker.feature.overlay.clock.getCurrentTime
import com.duycomp.autoclicker.feature.overlay.managerView
import com.duycomp.autoclicker.feature.overlay.utils.Movement
import com.duycomp.autoclicker.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.TimeZone
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var timeZoneOffset: Long =
        TimeZone.getDefault().getOffset(System.currentTimeMillis()).toLong()

    private var homeClockOffset by mutableIntStateOf(0)

    var isAccessibilityNotify by mutableStateOf(false)
        private set

    fun updateAccessibilityNotify(value: Boolean) {
        isAccessibilityNotify = value
    }

    val userDataUiState: StateFlow<UserDataUiState> =
        userDataRepository.userData.map { userData ->
            UserDataUiState.Success(userData = userData)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserDataUiState.Loading,
        )

    init {
        viewModelScope.launch {
            userDataRepository.userData.collect { userData ->
                if (!userData.isManualClockOffset) {
                    when (val result = ClockOffsetRepository().getClockOffset()) {
                        is Result.Success -> clockOffset = result.data.toInt()
                        is Result.Error -> {
                            Log.e(TAG, "Error getting clock offset: ${result.exception}")
                        }
                        is Result.Loading -> TODO()
                    }
                }

                if (!userData.isManualZoneOffset) {
                    userDataRepository.setZoneOffset(timeZoneOffset)
                } else {
                    timeZoneOffset = userData.zoneOffset
                }
            }
        }
    }

//    val clock: StateFlow<String> =
//        processingTime(
//            offset = homeClockOffset,
//            intervalEmit = 1000,
//            pattern = "HH:mm:ss"
//        ).flowOn(Dispatchers.IO)
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(),
//                initialValue = "Loading...",
//            )

    @SuppressLint("SimpleDateFormat")
    val clock: StateFlow<String> = flow {
        while (true) {
            emit(SimpleDateFormat("HH:mm:ss.S").format(getCurrentTime() - clockOffset))
            delay(1000)
        }
//        delay(5000)
//        Log.d("TAG", "processingTime: $clockOffset")
//        var preCurrent: Long = getCurrentTime() - clockOffset
//        var finalResetTime = preCurrent
//        var timeOld = getCurrentTime()
//        while (true) {
//            emit(SimpleDateFormat("HH:mm:ss").format(preCurrent))
//
//            delay(1000)
//            val calculateTime = getCurrentTime() - timeOld
//            preCurrent += calculateTime
//            timeOld = getCurrentTime()
//            if(preCurrent - finalResetTime > 10000) {
//
//                preCurrent = getCurrentTime() - clockOffset
//                finalResetTime = preCurrent
//                timeOld = getCurrentTime()
//            }
//        }
    }.flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "Loading...",
        )

    val isOverlaying: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(
            key = IS_OVERLAYING,
            initialValue = managerView.controller != null
        )
//    var isOverlaying: Boolean by mutableStateOf(isOverlay.value)

    fun onStartButtonClick(context: Context) {
        if (isOverlaying.value) onCloseOverlay()
        else onStartOverlay(context)
    }

    fun onStartOverlay(context: Context) {
        if (AcAccessibility.self != null) {
            AcAccessibility.windowManager?.also { windowManager ->
                savedStateHandle.set(key = IS_OVERLAYING, value = true)
                managerView.createControllerView(context)
                managerView.controller?.also {

                    windowManager.addView(
                        it.view,
                        it.layout,
                    )
                    Movement().add(
                        view = it.view,
                        layoutParams = it.layout,
                        windowManager = windowManager,
                    )
                }
            }
        } else {
            updateAccessibilityNotify(true)
        }
    }

    fun openAccessibilitySetting(context: Context) {
        val settingIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        context.startActivity(settingIntent)
//        updateAccessibilityNotify(false)
    }

    private fun onCloseOverlay() {
        savedStateHandle.set(key = IS_OVERLAYING, value = false)

//        managerView.removeAllOverlayView(AcAccessibility.windowManager!!)
    }

    fun onDurationClickChange(value: Long) = viewModelScope.launch {
        userDataRepository.setDurationClick(value)
    }

    fun onIntervalClickChange(value: Long) = viewModelScope.launch {
        userDataRepository.setIntervalClick(value)
    }

    fun onInfinityLoopChange(value: Boolean) = viewModelScope.launch {
        userDataRepository.setInfinityLoop(value)
    }

    fun onLoopChange(value: Int) = viewModelScope.launch {
        userDataRepository.setLoop(value)
    }


}

sealed interface UserDataUiState {
    data object Loading : UserDataUiState

    data class Success(
        val userData: UserData,
    ) : UserDataUiState

}

const val TAG = "HomeViewModel"
const val IS_OVERLAYING = "isOverlaying"