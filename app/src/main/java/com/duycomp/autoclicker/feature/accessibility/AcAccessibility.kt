package com.duycomp.autoclicker.feature.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AcAccessibility: AccessibilityService() {

    @Inject lateinit var userDataRepository: UserDataRepositoryImpl
    companion object {
        var windowManager : WindowManager? = null
        @SuppressLint("StaticFieldLeak")
        var self: AcAccessibility? = null
        var acUserDataRepository: UserDataRepositoryImpl? = null
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
        Toast.makeText(this.applicationContext, "Đã kết nối", Toast.LENGTH_SHORT).show()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        self = this
        acUserDataRepository = this.userDataRepository

//        windowManager.removeView()
    }

    override fun onDestroy() {
        super.onDestroy()
//        handlerThread.quitSafely()
        stopSelf()
    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt: ")
        Toast.makeText(this.applicationContext, "Lỗi", Toast.LENGTH_SHORT).show()

    }
}

const val TAG = "AcAccessibility"