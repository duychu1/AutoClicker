package com.duycomp.autoclicker.model

import android.graphics.Point
import android.view.MotionEvent
import android.view.WindowManager
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Position(var fx: Float, var fy: Float) {

    val x: Int
        get() = fx.toInt()

    val y: Int
        get() = fy.toInt()

    operator fun plus(p: Position) = Position(fx + p.fx, fy + p.fy)
    operator fun minus(p: Position) = Position(fx - p.fx, fy - p.fy)
    fun toPoint(): Point = Point(x, y)
}

fun Point.asPosition(): Position = Position(fx= x.toFloat(), fy = y.toFloat())

val MotionEvent.position: Position
    get() = Position(rawX, rawY)

var WindowManager.LayoutParams.position: Position
    get() = Position(x.toFloat(), y.toFloat())
    set(value) {
        x = value.x
        y = value.y
    }
