package com.duycomp.autoclicker.feature.overlay

import android.content.Context
import com.duycomp.autoclicker.feature.overlay.controller.controllerLayout
import com.duycomp.autoclicker.feature.overlay.controller.controllerView
import com.duycomp.autoclicker.model.ViewLayout

open class ManagerView() {
    var controller: ViewLayout? = null
    var time: ViewLayout? = null
    var setting: ViewLayout? = null
    var folder: ViewLayout? = null
    var pointSettingDialog: ViewLayout? = null
    var pointView: ViewLayout? = null
    fun createControllerView(context: Context) {
        controller = ViewLayout(
            view = controllerView(context),
            layout = controllerLayout()
        )
    }

}

val managerView = object : ManagerView() {}