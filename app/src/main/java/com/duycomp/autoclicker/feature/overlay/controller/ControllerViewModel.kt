package com.duycomp.autoclicker.feature.overlay.controller

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.accessibility.AcAccessibility
import com.duycomp.autoclicker.feature.accessibility.isClick
import com.duycomp.autoclicker.feature.overlay.clock.clockOffset
import com.duycomp.autoclicker.feature.overlay.folder_config.configSavedDialogView
import com.duycomp.autoclicker.feature.overlay.managerView
import com.duycomp.autoclicker.feature.overlay.setting.settingDialogView
import com.duycomp.autoclicker.feature.overlay.target.ManagerTargets
import com.duycomp.autoclicker.feature.overlay.target.dialogLayout
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.model.TimerSchedule
import com.duycomp.autoclicker.model.ViewLayout
import com.duycomp.autoclicker.model.startTargetsPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
//    private val userDataRepository: UserDataRepositoryImpl
) : ViewModel() {
    private val windowManager: WindowManager = AcAccessibility.windowManager!!
    private val managerTargets: ManagerTargets = ManagerTargets()
    private val userDataRepository = AcAccessibility.acUserDataRepository!!
    private val configDatabaseRepository = AcAccessibility.acConfigDatabaseRepository!!

    private val handlerThread: HandlerThread = HandlerThread("Click Thread")

//    private val handlerThread = AcAccessibility.handlerThread

    //    private val _configClick = MutableStateFlow(ConfigClick())
    private val _targetClick = MutableStateFlow(
        TargetClick(
            position = startTargetsPosition,
            intervalClick = 100L,
            durationClick = 10L,
        )
    )

//    val config = _configClick.asStateFlow()

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

    private var configClick: ConfigClick = ConfigClick()

    private val onConfigChange: (ConfigClick) -> Unit = {
        configClick = it
    }

    private var defaultTargetClick: TargetClick = TargetClick(
        position = startTargetsPosition,
        intervalClick = 100L,
        durationClick = 10L
    )

    var zoneOffset: Long = TimeZone.getDefault().getOffset(System.currentTimeMillis()).toLong()
//    var clockOffset: Long = 0

    init {
        handlerThread.start()
        viewModelScope.launch {
            userDataRepository.userData.collectLatest {
                configClick = configClick.copy(
                    nLoop = it.nLoop,
                    isInfinityLoop = it.isInfinityLoop,
                    timerSchedule = TimerSchedule(earlyClick = it.earlyTime),
                )
                Log.d(TAG, "init: ")
//                _targetClick.value = _targetClick.value.copy(
//                    intervalClick = it.intervalClick,
//                    durationClick = it.durationClick
//                )
                defaultTargetClick = defaultTargetClick.copy(
                    intervalClick = it.intervalClick,
                    durationClick = it.durationClick
                )

                zoneOffset = it.zoneOffset
//                clockOffset = it.clockOffset
            }
        }
    }

    var isPlaying by mutableStateOf(false)
        private set

//    var isPlaying by Delegates.observable(initialValue = false, onChange = {_, old, new ->
//        if (!new)
//        managerTargets.touchTarget(windowManager, configClick.targetsData)
//    })

    //    val handler = Handler(handlerThread.looper)
    fun onPlayClick(value: Boolean) {
        isPlaying = value
        isClick = value
        if (isPlaying) {
            managerTargets.unTouchTarget(windowManager, configClick.targetsData)
            AcAccessibility.acAction!!.startClick(
                handler = Handler(handlerThread.looper),
                configClick = configClick,
                clockOffset = clockOffset,
                zoneOffset = zoneOffset,
                isRunning = isPlaying,
                onFinish = {
                    isPlaying = false
                    isClick = false
                    Handler(Looper.getMainLooper()).post {
                        managerTargets.touchTarget(windowManager, configClick.targetsData)
                    }
                }
            )
        } else {
            managerTargets.touchTarget(windowManager, configClick.targetsData)
        }

    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onAddClick(context: Context) {
        managerTargets.addTarget(
            context,
            windowManager,
            defaultTargetClick,
            configClick.targetsData
        )
    }

    fun onRemoveClick() {
        managerTargets.removeTarget(windowManager, configClick.targetsData)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onSettingClick(context: Context) {
        ViewLayout(
            settingDialogView(
                context = context,
                windowManager = windowManager,
                configClick = configClick,
                userDataRepository = userDataRepository,
                configDatabaseRepository = configDatabaseRepository,
            ),
            dialogLayout()
        ).addViewToWindowManager(windowManager)
    }

    private var isShowClock = false
    fun onClockClick(context: Context) {
        isShowClock = !isShowClock
        if (isShowClock) {
            managerView.addClockView(context, windowManager)
        } else {
            managerView.removeClockView(windowManager)
        }

    }

    fun onFolderClick(context: Context) {
        ViewLayout(
            configSavedDialogView(
                context = context,
                windowManager = windowManager,
                configClick = configClick,
                onConfigChange = onConfigChange,
                managerTargets = managerTargets,
                userDataRepository = userDataRepository,
                configDatabaseRepository = configDatabaseRepository,
            ),
            dialogLayout()
        ).addViewToWindowManager(windowManager)
    }

    fun onCloseClick(context: Context, controllerView: ComposeView) {
        managerTargets.removeAllTargets(windowManager, configClick.targetsData)
        managerView.removeClockView(windowManager)
        windowManager.removeView(controllerView)
    }

    override fun onCleared() {
        managerTargets.removeAllTargets(windowManager, configClick.targetsData)
        managerView.removeClockView(windowManager)
        handlerThread.quitSafely()
        super.onCleared()
    }
}

private const val TAG = "ControllerViewModel"