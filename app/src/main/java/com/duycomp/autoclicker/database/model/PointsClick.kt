package com.duycomp.autoclicker.database.model

import android.graphics.Point
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PointsClick(
    val points: List<PointClick>
)



