package com.duycomp.autoclicker.feature.overlay.controller

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import com.duycomp.autoclicker.feature.overlay.target.HEIGHT_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.pointPx

fun controllerLayout(): WindowManager.LayoutParams {

    val params = WindowManager.LayoutParams()
    params.apply {
            x = pointPx
            y = HEIGHT_SCREEN /2 - pointPx /2
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.TOP or Gravity.START
        type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        format = PixelFormat.TRANSLUCENT
        flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    }

    return params
}