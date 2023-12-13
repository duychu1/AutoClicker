package com.duycomp.autoclicker.feature.overlay

import android.content.Context
import android.view.View
import android.view.WindowManager
import com.duycomp.autoclicker.feature.overlay.clock.clockLayout
import com.duycomp.autoclicker.feature.overlay.clock.clockView
import com.duycomp.autoclicker.feature.overlay.controller.controllerLayout
import com.duycomp.autoclicker.feature.overlay.controller.controllerView
import com.duycomp.autoclicker.feature.overlay.target.dialogLayout
import com.duycomp.autoclicker.feature.overlay.target.targetSettingDialogView
import com.duycomp.autoclicker.model.TargetData
import com.duycomp.autoclicker.model.ViewLayout

open class ManagerView() {
    var controller: ViewLayout? = null
    var clock: ViewLayout? = null
    var setting: ViewLayout? = null
    var folder: ViewLayout? = null
    var targetSettingDialog: ViewLayout? = null
    var targetsView: MutableList<View> = mutableListOf()

    fun createControllerView(context: Context) {
        controller = ViewLayout(
            view = controllerView(context),
            layout = controllerLayout()
        )
    }
    private fun removeControllerView(windowManager: WindowManager) {
        this.controller?.also {
            windowManager.removeView(it.view)
        }
        this.controller = null
    }

    private fun removeAllTargetsView(windowManager: WindowManager) {
        this.targetsView.forEach {
            windowManager.removeView(it)
        }
        targetsView.clear()
    }

    fun addTargetSettingDialogToWindowManager(
        context: Context,
        targetData: TargetData,
        windowManager: WindowManager
    ) {
        targetSettingDialog = ViewLayout(
            targetSettingDialogView(context,targetData, windowManager),
            dialogLayout()
        )

        targetSettingDialog!!.addViewToWindowManager(windowManager)
    }
    fun removeTargetSettingDialog(windowManager: WindowManager) {
        this.targetSettingDialog!!.also {
            windowManager.removeView(it.view)
        }
        this.targetSettingDialog = null
    }

    fun removeAllOverlayView(windowManager: WindowManager) {
        removeClockView(windowManager)
//        removeAllTargetsView(windowManager)
        removeControllerView(windowManager)
    }

    fun addClockView(context: Context, windowManager: WindowManager) {
        clock = ViewLayout(
            view = clockView(context),
            layout = clockLayout()
        )
        clock!!.addViewAndMovement(windowManager)
    }
    fun removeClockView(windowManager: WindowManager) {
        this.clock?.also {
            windowManager.removeView(it.view)
        }
        this.clock = null
    }

}

val managerView = object : ManagerView() {  }

fun WindowManager.acAddView(view: View, layout: WindowManager.LayoutParams) {
    this.addView(view, layout)
//    managerView.targetsView.add(view)
}

fun WindowManager.acRemoveView(view: View) {
    this.removeView(view)
//    managerView.targetsView.remove(view)
}