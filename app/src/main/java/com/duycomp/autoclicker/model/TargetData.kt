package com.duycomp.autoclicker.model

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.overlay.Movement
import com.duycomp.autoclicker.feature.overlay.asPosition
import com.duycomp.autoclicker.feature.overlay.target.HEIGHT_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.WIDTH_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.pointPx
import com.duycomp.autoclicker.feature.overlay.target.targetLayout
import com.duycomp.autoclicker.feature.overlay.target.targetView

data class TargetData(
    var position: Point = startTargetsPosition,
    var intervalClick:Long = 1000L,
    var durationClick:Long = 10L,
    var viewLayout: ViewLayout,
) {
    fun updatePosition(position: Point) {
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
    fun addViewAndMovement(windowManager: WindowManager) {
        this.viewLayout.addViewToWindowManager(windowManager)
        Movement().addTarget(windowManager = windowManager, targetData = this)
    }

}

fun createViewLayout(context: Context, number: Int, position: Point = startTargetsPosition): ViewLayout =
    ViewLayout(targetView(context, number), targetLayout(position.asPosition()))

fun TargetClick.asModel(context: Context, number: Int): TargetData =
    TargetData(
        position = position,
        intervalClick = intervalClick,
        durationClick = durationClick,
        viewLayout = createViewLayout(context, number, position)
    )

val startTargetsPosition = Point(
    (WIDTH_SCREEN /2 - pointPx /2),
    (HEIGHT_SCREEN /2 - pointPx /2)
)