package com.duycomp.autoclicker.model

import android.content.Context
import com.duycomp.autoclicker.database.ClickerConfigEntity

data class ConfigClick(
    var id: Int = -1,
    var order: Int = 0,
    var configName: String = "cau hinh 1",
    var nLoop: Int = 1,
    var isInfinityLoop: Boolean = false,
    var timerSchedule: TimerSchedule = TimerSchedule(),
    val targetsData: MutableList<TargetData> = mutableListOf(),
) {
    fun asEntities(): ClickerConfigEntity {
        return ClickerConfigEntity(
            id, order, configName, nLoop, isInfinityLoop, targetsData.map { it.asEntity() }, timerSchedule
        )
    }

    fun updateConfigName(value:String) {
        this.configName = value
    }

    fun updateLoop(value: Int) {
        this.nLoop = value
    }

    fun updateInfinityLoop(value: Boolean) {
        this.isInfinityLoop = value
    }

    fun updateTimerSchedule(timerSchedule: TimerSchedule) {
        this.timerSchedule = timerSchedule
    }
}

fun ClickerConfigEntity.asModel(context: Context): ConfigClick {
    return ConfigClick(
        id = id,
        order = order,
        configName = configName,
        nLoop = nLoop,
        isInfinityLoop = isInfinityLoop,
        timerSchedule = timerSchedule,
        targetsData = targetsClick.mapIndexed { index, targetClick ->
            targetClick.asModel(context, index)
        }.toMutableList()
    )
}

