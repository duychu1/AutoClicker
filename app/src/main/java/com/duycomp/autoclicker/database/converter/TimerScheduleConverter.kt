package com.duycomp.autoclicker.database.converter

import androidx.room.TypeConverter
import com.duycomp.autoclicker.model.TimerSchedule
import com.squareup.moshi.Moshi

class TimerScheduleConverter {
    private val moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(TimerSchedule::class.java)

    @TypeConverter
    fun fromTimerSchedule(timerSchedule: TimerSchedule): String {
        return jsonAdapter.toJson(timerSchedule)
    }

    @TypeConverter
    fun toTimerSchedule(str: String): TimerSchedule {
        return jsonAdapter.fromJson(str)!!
    }
}