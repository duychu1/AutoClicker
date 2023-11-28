package com.duycomp.autoclicker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.duycomp.autoclicker.database.converter.TargetClickConverter
import com.duycomp.autoclicker.database.converter.TimerScheduleConverter


@Database(entities = [ClickerConfigEntity::class], version = 1)
@TypeConverters(TargetClickConverter::class, TimerScheduleConverter::class)
abstract class ClickerConfigDB : RoomDatabase() {
    abstract fun clickerConfigDao(): ClickerConfigDao
}