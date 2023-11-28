package com.duycomp.autoclicker.model

import android.view.View
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import com.duycomp.autoclicker.feature.overlay.Movement
import com.duycomp.autoclicker.feature.overlay.Position

data class ViewLayout(
    var view: View,
    var layout: WindowManager.LayoutParams
) {
    fun addViewToWindowManager(windowManager: WindowManager) {
        windowManager.addView(view,layout)
    }

//    @ExperimentalComposeUiApi
//    fun addViewAndMovement(windowManager: WindowManager, position: Position) {
//        windowManager.addView(view,layout)
//        Movement().addTarget(windowManager)
//    }
}
