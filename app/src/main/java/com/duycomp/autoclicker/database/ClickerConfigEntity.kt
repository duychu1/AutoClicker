package com.duycomp.autoclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.model.TimerSchedule

const val DB_NAME = "configuration_clicker"
@Entity(tableName = DB_NAME)
data class ClickerConfigEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val order: Int= id,
    val configName: String,
    val nLoop: Int,
    val isInfinityLoop: Boolean,
    val targetsClick: List<TargetClick>,
    val timerSchedule: TimerSchedule
)




