package com.duycomp.autoclicker.model

import android.content.Context
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.overlay.acAddView
import com.duycomp.autoclicker.feature.overlay.target.HEIGHT_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.WIDTH_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.rectPointSize
import com.duycomp.autoclicker.feature.overlay.target.targetLayout
import com.duycomp.autoclicker.feature.overlay.target.targetView
import com.duycomp.autoclicker.feature.overlay.utils.Movement

data class TargetData(
    var position: Position = startTargetsPosition,
    var intervalClick:Long = 100L,
    var durationClick:Long = 10L,
    var viewLayout: ViewLayout,
) {
    fun updatePosition(position: Position) {
        this.position = position
    }

    fun updateInterval(interval: Long){
        this.intervalClick = interval
    }

    fun updateDuration(duration: Long){
        this.durationClick = duration
    }

    fun asEntity(): TargetClick = TargetClick(position, intervalClick, durationClick)

    @OptIn(ExperimentalComposeUiApi::class)
    fun addViewAndMovement(windowManager: WindowManager, context: Context) {
        windowManager.acAddView(this.viewLayout.view, this.viewLayout.layout)
        Movement().addTargetMovement(windowManager = windowManager, targetData = this, context = context)
    }

}

fun createViewLayout(context: Context, number: Int, position: Position = startTargetsPosition): ViewLayout =
    ViewLayout(targetView(context, number), targetLayout(position))

fun TargetClick.asModel(context: Context, number: Int, windowManager: WindowManager): TargetData {
    val target = TargetData(
        position = position,
        intervalClick = intervalClick,
        durationClick = durationClick,
        viewLayout = createViewLayout(context, number, position)
    )
    target.addViewAndMovement(windowManager = windowManager, context = context)
    return target
}

val startTargetsPosition = Position(
    ((WIDTH_SCREEN /2 - rectPointSize /2).toFloat()),
    ((HEIGHT_SCREEN /2 - rectPointSize /2).toFloat())
)