package com.duycomp.autoclicker.database.model

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class TimerSchedule(
    var isTimer: Boolean = false,
    var time: Date,
    var earlyClick: Int = 300,
)
