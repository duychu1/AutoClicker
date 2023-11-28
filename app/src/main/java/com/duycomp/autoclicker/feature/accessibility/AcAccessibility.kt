package com.duycomp.autoclicker.feature.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AcAccessibility : AccessibilityService() {
    companion object {
        var windowManager : WindowManager? = null
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onCreate() {
        super.onCreate()
//        handlerThread.start()
        Log.d(TAG, "onCreate: ")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onServiceConnected() {
        Log.d(TAG, "onServiceConnected: ")
        super.onServiceConnected()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        context = this

//        windowManager.removeView()
    }

    override fun onDestroy() {
        super.onDestroy()
//        handlerThread.quitSafely()
        stopSelf()
    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt: ")
    }
}

const val TAG = "AcAccessibility"