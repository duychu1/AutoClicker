package com.duycomp.autoclicker.database.converter

import androidx.room.TypeConverter
import com.duycomp.autoclicker.database.model.TargetClick
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class TargetClickConverter {
    private val moshi = Moshi.Builder().build()
    private val type = Types.newParameterizedType(List::class.java, TargetClick::class.java)
    private val jsonAdapter = moshi.adapter<List<TargetClick>>(type)
    @TypeConverter
    fun fromPointsClick(targetsClick: List<TargetClick>): String{
        return jsonAdapter.toJson(targetsClick)
    }

    @TypeConverter
    fun toPointsClick(str: String): List<TargetClick> {
        return jsonAdapter.fromJson(str)!!
    }
}