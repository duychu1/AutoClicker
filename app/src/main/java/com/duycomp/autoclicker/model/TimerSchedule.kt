package com.duycomp.autoclicker.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimerSchedule(
    val isTimer: Boolean = false,
    val timeMs: Long = 0,
    val earlyClick: Long = 300L,
) {

}
