package com.duycomp.autoclicker.model

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import androidx.compose.ui.ExperimentalComposeUiApi
import com.duycomp.autoclicker.database.model.TargetClick
import com.duycomp.autoclicker.feature.overlay.acAddView
import com.duycomp.autoclicker.feature.overlay.target.HEIGHT_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.WIDTH_SCREEN
import com.duycomp.autoclicker.feature.overlay.target.pointPx
import com.duycomp.autoclicker.feature.overlay.target.targetLayout
import com.duycomp.autoclicker.feature.overlay.target.targetView
import com.duycomp.autoclicker.feature.utils.Movement

data class TargetData(
    var position: Point = startTargetsPosition,
    var intervalClick:Long = 100L,
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
    fun addViewAndMovement(windowManager: WindowManager, context: Context) {
        windowManager.acAddView(this.viewLayout.view, this.viewLayout.layout)
        Movement().addTarget(windowManager = windowManager, targetData = this, context = context)
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