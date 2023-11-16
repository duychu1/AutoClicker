package com.duycomp.autoclicker.database.model

import android.graphics.Point
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PointClick (
    var position: Point,
    var delayClick:Long,
    var durationClick:Long,
) {
    fun updatePosition(p: Point) {
        position = p
    }

    fun updateDelay(delay:Long){
        delayClick = delay
    }
}
