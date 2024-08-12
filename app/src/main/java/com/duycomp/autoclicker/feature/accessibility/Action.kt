package com.duycomp.autoclicker.feature.accessibility

import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.graphics.Path
import android.os.Handler
import android.util.Log
import com.duycomp.autoclicker.feature.overlay.clock.clockOffset
import com.duycomp.autoclicker.feature.overlay.target.rectPointSize
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.model.TargetData
import com.duycomp.autoclicker.model.TimerSchedule
import com.duycomp.autoclicker.model.toStringFormatHmsMs
import java.text.SimpleDateFormat

var isClick = false

class Action(val accessibility: AcAccessibility) {

    @SuppressLint("SimpleDateFormat", "SuspiciousIndentation")
    fun startClick(
        handler: Handler,
        configClick: ConfigClick,
        clockOffset: Int,
        zoneOffset: Long,
        isRunning: Boolean,
        onFinish: () -> Unit
    ) {

        val runnable = Runnable {
            Log.d(TAG, "startClick: timeIn  = ${System.currentTimeMillis()}")
            Log.d(TAG, "startClick: time = ${SimpleDateFormat("HH:mm:ss.S").format(System.currentTimeMillis() - clockOffset)}")

            clickForEach(
                targetsClick = configClick.targetsData,
                nLoop = configClick.nLoop,
                isRunning = isRunning
            )
            onFinish()
        }

        var delayTime: Long = 150

//        Toast.makeText(
//            appcontext, "Đang hẹn:  " +
//                    "%02d".format(timerhms.hh) + " : " +
//                    "%02d".format(timerhms.mm) + " : " +
//                    "%02d".format(timerhms.ss), Toast.LENGTH_SHORT
//        ).show()
        Log.d(TAG, "startClick: ${configClick.timerSchedule}")

        if (configClick.timerSchedule.isTimer) {
            val timerSchedule = configClick.timerSchedule

            Log.d(
                TAG,
                "startClick: scheduleTime: ${timerSchedule.timeMs.toStringFormatHmsMs()}"
            )
            val systemDayMillis = (System.currentTimeMillis() + zoneOffset) % dayInMillis
            Log.d(TAG, "startClick: clockOffset = $clockOffset")
            delayTime = calculateDelay(delayTime, timerSchedule, systemDayMillis, clockOffset)

            Log.d(TAG, "startClick: systemTime: ${systemDayMillis.toStringFormatHmsMs()}")
            Log.d(
                TAG,
                "startClick: waiting: delayTime = $delayTime = ${delayTime.toStringFormatHmsMs()}"
            )
            Log.d(TAG, "startClick: timeout = ${System.currentTimeMillis()}")

            handler.postDelayed(runnable, delayTime)

        } else {
            handler.postDelayed(runnable, delayTime)
        }
    }

    private fun calculateDelay(
        delayTime: Long,
        timerSchedule: TimerSchedule,
        systemDayMillis: Long,
        clockOffset: Int
    ): Long {
        var delayTime1 = delayTime
        delayTime1 =
            timerSchedule.timeMs - systemDayMillis + clockOffset - timerSchedule.earlyClick

        if (delayTime1 < 0) delayTime1 += dayInMillis
        return delayTime1
    }

//    fun initClick(targetsClick: MutableList<TargetData>, nLoop: Int = 1) {
//        if(isClickForEach) {
//            try {
//                clickForEach(targetsClick, nLoop)
//            } catch (e: Exception) {
//                isClickForEach = false
//            }
//        } else {
//            try {
//                clickNonForEach(targetsClick, nLoop)
//            } catch (e: Exception) {
//                isClickForEach = true
//            }
//        }
//    }

    private fun clickForEach(
        targetsClick: MutableList<TargetData>,
        nLoop: Int = 1,
        isRunning: Boolean = true
    ) {
        repeat(nLoop) {
            targetsClick.forEachIndexed {index, element ->
                if (!isClick) {
                    return
                }
                Log.d(TAG, "startClick: timeClick = ${SimpleDateFormat("HH:mm:ss.S").format(System.currentTimeMillis() - clockOffset)}")
                Log.d(TAG, "clickForEach:  timeOn: ${System.currentTimeMillis()}")
                Log.d(TAG, "clickForEach: layoutFlags: ${element.viewLayout.layout.flags}")
                clickRect(
                    x = element.position.fx,
                    y = element.position.fy,
                    duration = element.durationClick
                )
                Log.d(TAG, "clickForEach: $index")
                Thread.sleep(element.intervalClick)
            }
        }

    }

    private fun clickNonForEach(targetsClick: MutableList<TargetData>, nLoop: Int = 1) {
        repeat(nLoop) {
            for (i in 0 until targetsClick.size) {
//                if (!isplay) {
//                    return
//                }
                clickRect(
                    x = targetsClick[i].position.fx,
                    y = targetsClick[i].position.fy,
                    duration = targetsClick[i].durationClick
                )
                Thread.sleep(targetsClick[i].intervalClick)
            }
        }
    }


    private fun clickRect(x: Float, y: Float, rectSize: Int = rectPointSize, duration: Long = 10) {
        val xPos = x + rectSize / 2
        val yPos = y + rectSize / 2

        clickPos(xPos, yPos)
    }

    private fun clickPos(x: Float, y: Float, duration: Long = 10) {
        val clickPath = Path()
        clickPath.moveTo(x, y)
        val clickStroke: GestureDescription.StrokeDescription =
            GestureDescription.StrokeDescription(clickPath, 10L, duration)
        val clickBuilder: GestureDescription.Builder = GestureDescription.Builder()
        clickBuilder.addStroke(clickStroke)

        val click: Boolean = accessibility.dispatchGesture(clickBuilder.build(), null, null)

        Log.d(TAG, "clickPos: $click ($x:$y)")
    }
}

const val dayInMillis = 24*60*60*1000