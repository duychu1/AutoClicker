package com.duycomp.autoclicker.feature.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.duycomp.autoclicker.data.ClickerConfigDatabaseRepositoryImpl
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AcAccessibility: AccessibilityService() {

    @Inject lateinit var userDataRepository: UserDataRepositoryImpl
    @Inject lateinit var configDatabaseRepository: ClickerConfigDatabaseRepositoryImpl
    companion object {
        var windowManager : WindowManager? = null
        @SuppressLint("StaticFieldLeak")
        var self: AcAccessibility? = null
        var acUserDataRepository: UserDataRepositoryImpl? = null
        var acConfigDatabaseRepository: ClickerConfigDatabaseRepositoryImpl? = null
        var acAction: Action? = null
//        val handlerThread: HandlerThread = HandlerThread("Click Thread")
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onCreate() {
        super.onCreate()
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
        acConfigDatabaseRepository = this.configDatabaseRepository
        acAction = Action(this)

//        clickPos1(400F, 505F,)
    }

    override fun onDestroy() {
        super.onDestroy()
        self = null
        stopSelf()
    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt: ")
        Toast.makeText(this.applicationContext, "Lỗi", Toast.LENGTH_SHORT).show()

    }

     fun clickPos1(x: Float, y: Float, duration: Long = 10) {
        val clickPath = Path()
        clickPath.moveTo(x, y)
        val clickStroke: GestureDescription.StrokeDescription =
            GestureDescription.StrokeDescription(clickPath, 10L, duration)
        val clickBuilder: GestureDescription.Builder = GestureDescription.Builder()
        clickBuilder.addStroke(clickStroke)

        val click: Boolean = dispatchGesture(clickBuilder.build(), null, null)

        Log.d(TAG, "clickPos: $click ($x:$y)")
    }

}

const val TAG = "AcAccessibility"