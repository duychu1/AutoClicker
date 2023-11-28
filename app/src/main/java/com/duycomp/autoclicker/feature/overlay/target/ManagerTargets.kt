package com.duycomp.autoclicker.feature.overlay.target

import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.overlay.Movement
import com.duycomp.autoclicker.model.TargetData
import com.duycomp.autoclicker.model.asModel
import com.duycomp.autoclicker.model.createViewLayout

class ManagerTargets() {

        @ExperimentalComposeUiApi
        fun showAllTargets(context: Context, windowManager: WindowManager, targetsData: MutableList<TargetData>) {
            targetsData.forEach{
                it.addViewAndMovement(windowManager)
            }
        }

        @ExperimentalComposeUiApi
        fun addTarget(context: Context, windowManager: WindowManager, newTarget: TargetClick, targetsData: MutableList<TargetData>) {
            targetsData.add(element = newTarget.asModel(context = context, number = targetsData.size + 1))
            targetsData.last().addViewAndMovement(windowManager)

        }

        fun removeTarget(windowManager: WindowManager, targetsData: MutableList<TargetData>) {
            windowManager.removeView(targetsData.last().viewLayout.view)
            targetsData.removeLast()
        }

        fun removeAllTargets(windowManager: WindowManager, targetsData: MutableList<TargetData>) {
            targetsData.forEach {
                windowManager.removeView(it.viewLayout.view)
            }
            targetsData.clear()
        }

        fun unTouchTarget(windowManager: WindowManager, targetsData: MutableList<TargetData>) {

            targetsData.forEach {
                it.viewLayout.layout.flags += WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                windowManager.updateViewLayout(it.viewLayout.view, it.viewLayout.layout)
            }

        }

        fun touchTarget(windowManager: WindowManager, targetsData: MutableList<TargetData>) {

            targetsData.forEach {
                it.viewLayout.layout.flags -= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                windowManager.updateViewLayout(it.viewLayout.view, it.viewLayout.layout)
            }

        }

        //forandroid12
        fun hideTarget( targetsData: MutableList<TargetData>) {
            targetsData.forEach {
                it.viewLayout.view.visibility = View.GONE
            }
//            isShowTarget = false
        }

        fun appearTarget( targetsData: MutableList<TargetData>) {
            targetsData.forEach {
                it.viewLayout.view.visibility = View.VISIBLE
            }
//            isShowPoint = true
        }

}