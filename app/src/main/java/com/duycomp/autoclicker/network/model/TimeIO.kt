package com.duycomp.autoclicker.network.model

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class TimeIO(
    val hour:Long,
    val minute:Long,
    val seconds:Long,
    val milliSeconds:Long,
    val timeZone: String,
)

fun fromJsonToTimeIO(strJson: String): TimeIO? {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(TimeIO::class.java)
    return adapter.fromJson(strJson)
}