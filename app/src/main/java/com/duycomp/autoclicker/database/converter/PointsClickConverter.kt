package com.duycomp.autoclicker.database.converter

import androidx.room.TypeConverter
import com.duycomp.autoclicker.database.model.PointsClick
import com.squareup.moshi.Moshi

class PointsClickConverter {
    private val moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(PointsClick::class.java)
    @TypeConverter
    fun fromPointsClick(pointsClick: PointsClick): String{
        return jsonAdapter.toJson(pointsClick)
    }

    @TypeConverter
    fun toPointsClick(str: String): PointsClick {
        return jsonAdapter.fromJson(str)!!
    }
}