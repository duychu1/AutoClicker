package com.duycomp.autoclicker.feature.overlay.target

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import com.duycomp.autoclicker.model.Position

const val rectPointSize = 120
const val WIDTH_SCREEN = 1080
const val HEIGHT_SCREEN = 2160

fun targetLayout(
    position: Position = Position(
                            (WIDTH_SCREEN /2 - rectPointSize /2).toFloat(),
                            (HEIGHT_SCREEN /2 - rectPointSize /2).toFloat()
                        )
): WindowManager.LayoutParams =
    WindowManager.LayoutParams().apply {
        y = position.y
        x = position.x
        width = rectPointSize
        height = rectPointSize
        gravity = Gravity.TOP or Gravity.START
        type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        format = PixelFormat.TRANSLUCENT
        flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
    }
