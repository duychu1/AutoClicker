package com.duycomp.autoclicker.model

import android.content.Context
import android.view.View
import android.view.WindowManager
import com.duycomp.autoclicker.feature.overlay.utils.Movement

data class ViewLayout(
    var view: View,
    var layout: WindowManager.LayoutParams
) {
    fun addViewToWindowManager(windowManager: WindowManager) {
        windowManager.addView(view,layout)
    }
    fun addViewAndMovement(windowManager: WindowManager) {
        windowManager.addView(view,layout)
        Movement().add(view,layout,windowManager)
    }

//    @ExperimentalComposeUiApi
//    fun addViewAndMovement(windowManager: WindowManager, position: Position) {
//        windowManager.addView(view,layout)
//        Movement().addTarget(windowManager)
//    }
    fun prev(context: Context) {

    }
}
