package com.duycomp.autoclicker.feature.overlay.clock

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import com.duycomp.autoclicker.feature.overlay.target.HEIGHT_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.pointPx

fun clockLayout(): WindowManager.LayoutParams =
    WindowManager.LayoutParams().apply {
        x = 50
        y = HEIGHT_SCREEN/4 - pointPx
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.TOP or Gravity.START
        type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        format = PixelFormat.TRANSLUCENT
        flags =
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    }
