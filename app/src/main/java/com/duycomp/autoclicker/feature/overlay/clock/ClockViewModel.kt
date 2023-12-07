package com.duycomp.autoclicker.feature.overlay.clock

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ClockViewModel : ViewModel() {
//    val time =  mutableStateOf("")
    var time = ""

    private val onTimeChange: (String) -> Unit = {
        viewModelScope.launch(Dispatchers.Main) {
            time = it
        }
    }

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            processingTime(onTimeChange)
//        }
//    }

/*    init {
        viewModelScope.launch(Dispatchers.IO) {
            processingTime().collect {
                withContext(Dispatchers.Main) {

                    time.value = it
                }
            }
        }
    }*/

}

@SuppressLint("SimpleDateFormat")
private suspend fun processingTime(onTimeChange: (String) -> Unit ) {
    var preCurrent: Long = getCurrentTime() - clockOffset
    var finalResetTime = preCurrent
    var timeOld = getCurrentTime()
    while (true) {
//        println(n)
//        n++
//        withContext(Dispatchers.Main) {
        onTimeChange(SimpleDateFormat("HH:mm:ss.S").format(preCurrent))
//        Log.d("TAG", "clockView: ${SimpleDateFormat("HH:mm:ss.S").format(preCurrent)}")
//        }
        delay(100)
        val calculateTime = getCurrentTime() - timeOld
        preCurrent += calculateTime
        timeOld = getCurrentTime()
        Log.d("TAG", "processingTime: ${Thread.currentThread().name}")

    }
}

/*
@SuppressLint("SimpleDateFormat")
private suspend fun processingTime(): Flow<String> = flow {
    var preCurrent: Long = getCurrentTime() - saiso
    var finalResetTime = preCurrent
    var timeOld = getCurrentTime()
    while (true) {
//        println(n)
//        n++
//        withContext(Dispatchers.Main) {
        emit(SimpleDateFormat("HH:mm:ss.S").format(preCurrent))
//        Log.d("TAG", "clockView: ${SimpleDateFormat("HH:mm:ss.S").format(preCurrent)}")
//        }
        delay(100)
        val calculateTime = getCurrentTime() - timeOld
        preCurrent += calculateTime
        timeOld = getCurrentTime()
        Log.d("TAG", "processingTime: ${Thread.currentThread().name}")

    }
}*/
