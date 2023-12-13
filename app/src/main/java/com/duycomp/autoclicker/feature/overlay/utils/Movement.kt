package com.duycomp.autoclicker.feature.overlay.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import com.duycomp.autoclicker.feature.overlay.target.HEIGHT_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.WIDTH_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.dialogLayout
import com.duycomp.autoclicker.feature.overlay.target.rectPointSize
import com.duycomp.autoclicker.feature.overlay.target.targetSettingDialogView
import com.duycomp.autoclicker.model.Position
import com.duycomp.autoclicker.model.TargetData
import com.duycomp.autoclicker.model.ViewLayout
import com.duycomp.autoclicker.model.position
import kotlin.math.abs


class Movement {

    @SuppressLint("ClickableViewAccessibility")
    fun add(
        view: View,
        layoutParams: WindowManager.LayoutParams,
        windowManager: WindowManager,
    ){
        var initial: Position? = null
        view.setOnTouchListener { view, e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    initial = layoutParams.position - e.position
                }
                MotionEvent.ACTION_MOVE -> {
                    initial?.let {
                        layoutParams.position = it + e.position
                        windowManager.updateViewLayout(view, layoutParams)
                    }
                }
                MotionEvent.ACTION_UP -> {

//                    Log.d(TAG, "moving: layout x= ${layoutParams.x}, y = ${layoutParams.y} ")
                    //Log.d(TAG, "moving: user x= ${e.x}, y= ${e.y} ")
                    initial = null
                }
            }
           // Log.d("paramsPosition", "showOverlay: ${layoutParams.x}, ${layoutParams.y}")
            false
        }
    }

//    @SuppressLint("ClickableViewAccessibility")
//    fun moving(
//        view: View,
//        windowManager: WindowManager,
//        layoutParams: WindowManager.LayoutParams,
//        position: Position
//    ){
//        var initial: Position? = null
//        view.setOnTouchListener {
//                view, e ->
//            //Toast.makeText(applicationContext, "view click", Toast.LENGTH_SHORT).show()
//            when (e.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    initial = layoutParams.position - e.position
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    initial?.let {
//                        layoutParams.position = it + e.position
//                        windowManager.updateViewLayout(view, layoutParams)
//                    }
//                }
//                MotionEvent.ACTION_UP -> {
//                    startBtnPosition.fx = layoutParams.position.fx
//                    startBtnPosition.fy = layoutParams.position.fy + rectPointSize/2
//
//                    Log.d(TAG, "moving: layout x= ${layoutParams.x}, y = ${layoutParams.y} ")
//                    //Log.d(TAG, "moving: user x= ${e.x}, y= ${e.y} ")
//                    initial = null
//                }
//            }
//            // Log.d("paramsPosition", "showOverlay: ${layoutParams.x}, ${layoutParams.y}")
//            false
//        }
//    }

    @ExperimentalComposeUiApi
    @SuppressLint("ClickableViewAccessibility")
    fun addTargetMovement(
        windowManager: WindowManager,
        targetData: TargetData,
        widthScreen: Int = WIDTH_SCREEN,
        heightScreen: Int = HEIGHT_SCREEN,
        rectSize: Int = rectPointSize,
        context: Context,
    ){
        val maxDurationClick = 200L
        var startPos: Position? = null
        var startTouchTime: Long? = null
        var initial: Position? = null
        val layoutParams = targetData.viewLayout.layout
//        layoutParams.position = Position(pointClick.position.x.toFloat(), pointClick.position.y.toFloat())

        targetData.viewLayout.view.setOnTouchListener { view, e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPos = layoutParams.position
                    initial = layoutParams.position - e.position
                    startTouchTime = getCurrentMillis()
                }
                MotionEvent.ACTION_MOVE -> {
                    initial?.let {
                        layoutParams.position = it + e.position
                        windowManager.updateViewLayout(view, layoutParams)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (layoutParams.x > widthScreen - (rectPointSize/2 + 9) ) {
                        layoutParams.x = widthScreen - (rectPointSize/2 + 9)
                    }
                    if (layoutParams.x < -(rectPointSize/2 - 9)) {
                        layoutParams.x = -(rectPointSize/2 - 9)
                    }
                    if (layoutParams.y > heightScreen - (rectPointSize/2 + 9) ) {
                        layoutParams.y = heightScreen - (rectPointSize/2 + 9)
                    }
                    if (layoutParams.y < -(rectPointSize/2 - 9)) {
                        layoutParams.y = -(rectPointSize/2 - 9)
                    }
                    windowManager.updateViewLayout(view, layoutParams)
                    targetData.updatePosition(layoutParams.position)

                    Log.d(TAG, "addTargetMovement: moving: layout x= ${targetData.viewLayout.layout.x}, y = ${targetData.viewLayout.layout.y} ")

                    initial = null
                    val durationClick = getCurrentMillis() - startTouchTime!!
                    if ((durationClick < maxDurationClick)
                        && (abs(layoutParams.x - startPos!!.x) < 50)
                        && (abs(layoutParams.y - startPos!!.y) < 50)

                    ) {

                        ViewLayout(
                            targetSettingDialogView(context,targetData, windowManager),
                            dialogLayout()
                        ).addViewToWindowManager(windowManager)

                    }
                }
            }
            // Log.d("paramsPosition", "showOverlay: ${targetData.viewLayout.layout.x}, ${targetData.viewLayout.layout.y}")
            false
        }
    }

    private fun getCurrentMillis(): Long = System.currentTimeMillis()

}

const val TAG = "Movement"