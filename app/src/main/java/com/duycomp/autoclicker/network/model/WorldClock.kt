package com.duycomp.autoclicker.network.model

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class WorldClock(
    val currentFileTime:Long,
    val utcOffset: String,
    val timeZoneName: String,
)

fun fromJsonToWorldClock(strJson: String): WorldClock? {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(WorldClock::class.java)
    return adapter.fromJson(strJson)
}
