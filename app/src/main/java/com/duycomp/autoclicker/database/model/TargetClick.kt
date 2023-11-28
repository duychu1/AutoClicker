package com.duycomp.autoclicker.database.model

import android.content.Context
import android.graphics.Point
import com.duycomp.autoclicker.feature.overlay.asPosition
import com.duycomp.autoclicker.feature.overlay.target.targetLayout
import com.duycomp.autoclicker.feature.overlay.target.targetView
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TargetClick (
    var position: Point,
    var intervalClick:Long,
    var durationClick:Long,
)