package com.duycomp.autoclicker.feature.overlay.target

import android.graphics.PixelFormat
import android.graphics.Point
import android.view.Gravity
import android.view.WindowManager
import com.duycomp.autoclicker.feature.overlay.Position

const val pointPx = 150
const val WIDTH_SCREEN = 1080
const val HEIGHT_SCREEN = 2160

fun targetLayout(
    position: Position = Position(
                            (WIDTH_SCREEN /2 - pointPx /2).toFloat(),
                            (HEIGHT_SCREEN /2 - pointPx /2).toFloat()
                        )
): WindowManager.LayoutParams =
    WindowManager.LayoutParams().apply {
        y = position.y
        x = position.x
        width = pointPx
        height = pointPx
        gravity = Gravity.TOP or Gravity.START
        type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        format = PixelFormat.TRANSLUCENT
        flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    }
