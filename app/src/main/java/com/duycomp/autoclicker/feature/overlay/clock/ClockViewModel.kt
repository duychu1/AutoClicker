package com.duycomp.autoclicker.feature.overlay.clock

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.common.result.Result
import com.duycomp.autoclicker.data.ClockOffsetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class ClockViewModel @Inject constructor() : ViewModel() {
    val clock: StateFlow<String> = processingTime(offset = clockOffset, intervalEmit = 100L)
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = "Loading...",
            )

}

@SuppressLint("SimpleDateFormat")
fun processingTime(
    offset: Int,
    intervalEmit: Long,
    pattern: String = "HH:mm:ss.S",
): Flow<String> = flow {
//    Log.d("TAG", "processingTime: $offset")
//    var preCurrent: Long = getCurrentTime() - offset
//    var finalResetTime = preCurrent
//    var timeOld = getCurrentTime()
//    while (true) {
//        emit(SimpleDateFormat(pattern).format(preCurrent))
//
//        delay(intervalEmit)
//        val calculateTime = getCurrentTime() - timeOld
//        preCurrent += calculateTime
//        timeOld = getCurrentTime()
////        Log.d("TAG", "processingTime: ${Thread.currentThread().name}")
//        if(preCurrent - finalResetTime > 10000) {
////            if(!isplay) {
////                CoroutineScope(Dispatchers.IO).launch {
////                    saiso = getDeltaTime()
////                }
////            }
//            preCurrent = getCurrentTime() - clockOffset
//            finalResetTime = preCurrent
//        }
//    }

    var finalResetTime = getCurrentTime()
    while (true) {
        val currentTime = getCurrentTime() - clockOffset
        emit(SimpleDateFormat(pattern).format(currentTime))
        delay(100)
        if (currentTime - finalResetTime > 30000) {
            CoroutineScope(Dispatchers.IO).launch {
                ClockOffsetRepository().getClockOffset().also {
                    if (it is Result.Success)
                        clockOffset = it.data.toInt()
                }
                finalResetTime = getCurrentTime()
            }
        }
    }
}

fun getCurrentTime(): Long = System.currentTimeMillis()
