package com.duycomp.autoclicker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.duycomp.autoclicker.database.converter.PointsClickConverter
import com.duycomp.autoclicker.database.converter.TimerScheduleConverter


@Database(entities = [ClickerConfig::class], version = 1)
@TypeConverters(PointsClickConverter::class, TimerScheduleConverter::class)
abstract class ClickerConfigDB : RoomDatabase() {
    abstract fun clickerConfigDao(): ClickerConfigDao
}