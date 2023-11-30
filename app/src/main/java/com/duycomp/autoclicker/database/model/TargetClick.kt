package com.duycomp.autoclicker.database.model

import android.graphics.Point
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TargetClick (
    var position: Point,
    var intervalClick:Long,
    var durationClick:Long,
)