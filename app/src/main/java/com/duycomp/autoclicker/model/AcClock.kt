package com.duycomp.autoclicker.model

data class AcClock(
    val isTimeManual: Boolean,
    val isOffsetManual: Boolean,
    val clockOffset: Long,
    val zoneOffset: Long,
)
