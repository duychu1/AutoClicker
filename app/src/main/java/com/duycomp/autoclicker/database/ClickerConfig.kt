package com.duycomp.autoclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.duycomp.autoclicker.database.model.PointsClick
import com.duycomp.autoclicker.database.model.TimerSchedule

const val DB_NAME = "configuration_clicker"
@Entity(tableName = DB_NAME)
data class ClickerConfig(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val order: Int,
    val configName: String,
    val nLoop: Int,
    val isInfinityLoop: Boolean,
    val pointsClick: PointsClick,
    val timerSchedule: TimerSchedule
)




