package com.duycomp.autoclicker.model

import android.content.Context
import com.duycomp.autoclicker.database.ClickerConfigEntity

data class ConfigClick(
    val id: Int = 0,
    val order: Int = 0,
    val configName: String = "cau hinh 1",
    val nLoop: Int = 1,
    val isInfinityLoop: Boolean = false,
    val timerSchedule: TimerSchedule = TimerSchedule(),
    val targetsData: MutableList<TargetData> = mutableListOf(),
) {
    fun asEntities(): ClickerConfigEntity {
        return ClickerConfigEntity(
            id, order, configName, nLoop, isInfinityLoop, targetsData.map { it.asEntity() }, timerSchedule
        )
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

