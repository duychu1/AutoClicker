package com.duycomp.autoclicker.feature.overlay.target

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.duycomp.autoclicker.feature.overlay.utils.MyLifecycleOwner
import com.duycomp.autoclicker.feature.overlay.utils.MyViewModelStoreOwner
import com.duycomp.autoclicker.ui.theme.AcIcons
import com.duycomp.autoclicker.ui.theme.Point

fun targetView(context: Context, num: Int) : ComposeView =
    composeToView(context = context, content = { TargetCompose(num) })
@Composable
fun TargetCompose(
    num: Int
) {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = AcIcons.pointClick,
            tint = Point,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Text(text = "$num", fontSize = 30.sp, color = Color.DarkGray)

    }
}



fun composeToView (
    context: Context,
    content: @Composable () -> Unit
): ComposeView {
    val composeView = ComposeView(context)
    composeView.setContent {
        content()
    }

    val viewModelStoreOwner = MyViewModelStoreOwner(ViewModelStore())
    val lifecycleOwner = MyLifecycleOwner()
    lifecycleOwner.performRestore(null)
    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    composeView.setViewTreeLifecycleOwner(lifecycleOwner)
    composeView.setViewTreeViewModelStoreOwner(viewModelStoreOwner)
    composeView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)

    return composeView
}



