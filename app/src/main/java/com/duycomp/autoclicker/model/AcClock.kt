package com.duycomp.autoclicker.model

import java.time.ZoneOffset

data class AcClock(
    val isManual: Boolean,
    val clockOffset: Long,
    val zoneOffset: ZoneOffset
)
