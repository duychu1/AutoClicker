package com.duycomp.autoclicker.model

import android.content.Context
import android.view.WindowManager
import com.duycomp.autoclicker.database.ClickerConfigEntity
import com.duycomp.autoclicker.database.model.TargetClick

data class ConfigClick(
    var id: Int = -1,
    var order: Int = 0,
    var configName: String = "Cấu hình 1",
    var nLoop: Int = 1,
    var isInfinityLoop: Boolean = false,
    var timerSchedule: TimerSchedule = TimerSchedule(),
    var targetsData: MutableList<TargetData> = mutableListOf(),
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

    fun defaultValue(): ConfigClick = ConfigClick(
        id = -1,
        order = 0,
        configName = "cau hinh 1",
        nLoop = 1,
        isInfinityLoop = false,
        timerSchedule = TimerSchedule(),

    )
}

fun ClickerConfigEntity.asModel(context: Context, windowManager: WindowManager): ConfigClick {
    return ConfigClick(
        id = id,
        order = order,
        configName = configName,
        nLoop = nLoop,
        isInfinityLoop = isInfinityLoop,
        timerSchedule = timerSchedule,
        targetsData = targetsClick.asModel(context,windowManager)
    )
}

fun List<TargetClick>.asModel(context: Context, windowManager: WindowManager): MutableList<TargetData> = this.mapIndexed { index, targetClick ->
    targetClick.asModel(context = context, number = index+1, windowManager)
}.toMutableList()

