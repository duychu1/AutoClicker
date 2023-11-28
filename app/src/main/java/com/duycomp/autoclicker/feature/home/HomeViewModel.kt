package com.duycomp.autoclicker.feature.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.feature.accessibility.AcAccessibility
import com.duycomp.autoclicker.feature.accessibility.checkAccessibilityPermission
import com.duycomp.autoclicker.feature.overlay.Movement
import com.duycomp.autoclicker.model.ViewLayout
import com.duycomp.autoclicker.feature.overlay.managerView
import com.duycomp.autoclicker.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val controllerViewLayout: ViewLayout,
    private val userDataRepository: UserDataRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
//    @Inject lateinit var acb: AcAccessibility

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

    val userDataUiState: StateFlow<UserDataUiState> =
        userDataRepository.userData.map { userData ->
            UserDataUiState.Success (
                userData = userData
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserDataUiState.Loading,
    )

    val isOverlaying: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(key = IS_OVERLAYING, initialValue = managerView.controller != null)
//    var isOverlaying: Boolean by mutableStateOf(isOverlay.value)

    fun onStartButtonClick(context: Context) {
        if (isOverlaying.value) onCloseOverlay()
        else onStartOverlay(context)
    }

    private fun onStartOverlay(context: Context) {

        if (
            checkAccessibilityPermission(
                context = context,
                accessibilityClass = AcAccessibility::class.java
            )
        ) {
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
            val settingIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            context.startActivity(settingIntent)
        }


    }

    private fun onCloseOverlay() {
        savedStateHandle.set(key = IS_OVERLAYING, value = false)

        AcAccessibility.windowManager?.also { windowManager ->
            windowManager.removeView(managerView.controller!!.view)
        }
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