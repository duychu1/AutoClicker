package com.duycomp.autoclicker.model

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class TimerSchedule(
    var isTimer: Boolean = false,
    var time: Date = Date(0),
    var earlyClick: Long = 300L,
) {

}
