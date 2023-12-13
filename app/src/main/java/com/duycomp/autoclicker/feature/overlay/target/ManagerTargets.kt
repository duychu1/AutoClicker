package com.duycomp.autoclicker.feature.overlay.target

import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.overlay.acRemoveView
import com.duycomp.autoclicker.model.TargetData
import com.duycomp.autoclicker.model.asModel

class ManagerTargets() {

        @ExperimentalComposeUiApi
        fun showAllTargets(context: Context, windowManager: WindowManager, targetsData: MutableList<TargetData>) {
            targetsData.forEach{
                it.addViewAndMovement(windowManager, context)
            }
        }

        @ExperimentalComposeUiApi
        fun addTarget(context: Context, windowManager: WindowManager, newTarget: TargetClick, targetsData: MutableList<TargetData>) {
            targetsData.add(element = newTarget.asModel(context = context, number = targetsData.size + 1, windowManager))
//            targetsData.last().addViewAndMovement(windowManager, context)

        }

        fun removeTarget(windowManager: WindowManager, targetsData: MutableList<TargetData>) {
            if (targetsData.isEmpty()) return

            windowManager.acRemoveView(targetsData.last().viewLayout.view)
            targetsData.removeLast()
        }

        fun removeAllTargets(windowManager: WindowManager, targetsData: MutableList<TargetData>) {
            targetsData.forEach {
                Log.d("TAG", "removeAllTargets: ${it.viewLayout.view}")
                windowManager.removeView(it.viewLayout.view)
            }
            targetsData.clear()
        }

        fun unTouchTarget(windowManager: WindowManager, targetsData: MutableList<TargetData>) {
            targetsData.forEach {
                it.viewLayout.layout.flags += WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                windowManager.updateViewLayout(it.viewLayout.view, it.viewLayout.layout)
//                Log.d(TAG, "unTouchTarget: layoutFlags: ${it.viewLayout.layout.flags}")
            }

        }

        fun touchTarget(windowManager: WindowManager, targetsData: MutableList<TargetData>) {

            targetsData.forEach {
                it.viewLayout.layout.flags -= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                windowManager.updateViewLayout(it.viewLayout.view, it.viewLayout.layout)
//                Log.d(TAG, "touchTarget: layoutFlags: ${it.viewLayout.layout.flags}")

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

const val TAG = "ManagerTargets"