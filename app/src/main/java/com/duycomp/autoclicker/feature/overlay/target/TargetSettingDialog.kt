package com.duycomp.autoclicker.feature.overlay.target

import android.content.Context
import android.graphics.PixelFormat
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.duycomp.autoclicker.R
import com.duycomp.autoclicker.feature.home.InputItem
import com.duycomp.autoclicker.feature.overlay.utils.MyLifecycleOwner
import com.duycomp.autoclicker.feature.overlay.utils.MyViewModelStoreOwner
import com.duycomp.autoclicker.model.TargetData
import com.duycomp.autoclicker.model.ViewLayout
import kotlin.math.roundToInt

@Composable
fun TargetSettingDialog(
    targetData: TargetData,
    onDismiss: () -> Unit = { },
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background)
            .padding(30.dp)
    ) {
        var interval: Long by remember {
            mutableLongStateOf(targetData.intervalClick)
        }

        var duration: Long by remember {
            mutableLongStateOf(targetData.durationClick)
        }

        val context = LocalContext.current

//        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.target_setting_title),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(20.dp))

        InputItem(
            title = stringResource(R.string.interval_click_title),
            value = interval,
            onValueChange = { interval = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f),
            thickness = 1.dp

        )

        Spacer(modifier = Modifier.height(20.dp))

        InputItem(
            title = stringResource(R.string.duration_click_title),
            value = duration,
            onValueChange = { duration = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.align(Alignment.End)) {
            TextButton(onClick = {

                onDismiss()
            }) {
                Text(text = "Thoát")
            }
            Button(onClick = {
                Toast.makeText(context, "Đã cập nhật", Toast.LENGTH_SHORT).show()
                targetData.updateInterval(interval)
                targetData.updateDuration(duration)
                onDismiss()
            }) {
                Text(text = "Cập nhật")
            }
        }

    }
}

fun targetSettingDialogView(
    context: Context,
    targetData: TargetData,
    windowManager: WindowManager,
): ComposeView {
    val composeView = ComposeView(context)
    composeView.setContent {
        TargetSettingDialog(
            targetData = targetData,
            onDismiss = {
                try {
                    windowManager.removeView(composeView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        )
    }

    composeView.actingLikeLifecycle()

    return composeView
}

fun ComposeView.actingLikeLifecycle() {
    val viewModelStoreOwner = MyViewModelStoreOwner(ViewModelStore())
    val lifecycleOwner = MyLifecycleOwner()
    lifecycleOwner.performRestore(null)
    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    this.setViewTreeLifecycleOwner(lifecycleOwner)
    this.setViewTreeViewModelStoreOwner(viewModelStoreOwner)
    this.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
}

@Preview
@Composable
fun PreTargetSetting() {
    val context = LocalContext.current
    TargetSettingDialog(targetData = TargetData(viewLayout = ViewLayout(View(context), dialogLayout())))
}

fun dialogLayout(): WindowManager.LayoutParams =
    WindowManager.LayoutParams().apply {
        width = (WIDTH_SCREEN* 0.9).roundToInt()
        height = WindowManager.LayoutParams.WRAP_CONTENT
        type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        format = PixelFormat.TRANSLUCENT
        flags =
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        dimAmount = 0.5f
    }
