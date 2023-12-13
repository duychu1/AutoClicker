package com.duycomp.autoclicker.feature.overlay.setting

import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.duycomp.autoclicker.R
import com.duycomp.autoclicker.data.ClickerConfigDatabaseRepositoryImpl
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.feature.home.BasicTextFieldUnderline
import com.duycomp.autoclicker.feature.home.BasicTextFieldUnderlineUnit
import com.duycomp.autoclicker.feature.home.DividerAlpha10
import com.duycomp.autoclicker.feature.home.LoopSetting
import com.duycomp.autoclicker.feature.overlay.target.actingLikeLifecycle
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.model.HmsMs
import com.duycomp.autoclicker.model.TimerSchedule
import com.duycomp.autoclicker.model.longToHmsMs
import com.duycomp.autoclicker.ui.theme.AutoClickerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalComposeUiApi
fun settingDialogView(
    context: Context,
    windowManager: WindowManager,
    configClick: ConfigClick,
    userDataRepository: UserDataRepositoryImpl,
    configDatabaseRepository: ClickerConfigDatabaseRepositoryImpl,
    scopeIO: CoroutineScope = CoroutineScope(Dispatchers.IO),
): ComposeView {
    val composeView = ComposeView(context)
    composeView.setContent {
        AutoClickerTheme {
            SettingDialog(
                configClick = configClick,
                onDismiss = {
                    try {
                        windowManager.removeView(composeView)
                    } catch (e: Exception) {
                        TODO("Not yet implemented")
                    }
                },
                onInsertConfigClick = {
                    scopeIO.launch {
                        val id = configDatabaseRepository.insert(it)
                        withContext(Dispatchers.Main) {
                            configClick.id = id.toInt()
                            configClick.order = id.toInt()
                        }
                    }


                },
                onUpdateConfigClick = {
                    scopeIO.launch {
                        configDatabaseRepository.update(it)
                    }
                },
                onUpdateEarlyClick = {
                    scopeIO.launch {
                        userDataRepository.setEarlyTime(it)
                    }
                }

            )
        }
    }

    composeView.actingLikeLifecycle()

    return composeView
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingDialog(
    configClick: ConfigClick,
    onDismiss: () -> Unit = {},
//    viewModel: SettingDialogViewModel,
    onInsertConfigClick: (ConfigClick) -> Unit = { },
    onUpdateConfigClick: (ConfigClick) -> Unit = { },
    onUpdateEarlyClick: (Long) -> Unit = { },
) {
//    val configClick by _configClick.collectAsStateWithLifecycle()
    var configName by remember {
        mutableStateOf(configClick.configName)
    }
    var timerSchedule by remember {
        mutableStateOf(configClick.timerSchedule)
    }
    var nLoop by remember {
        mutableIntStateOf(configClick.nLoop)
    }
    var isInfinityLoop by remember {
        mutableStateOf(configClick.isInfinityLoop)
    }

    fun onSettingConfigComponentsChange() {
        configClick.configName = configName
        configClick.timerSchedule = timerSchedule
        configClick.nLoop = nLoop
        configClick.isInfinityLoop = isInfinityLoop
    }

        SettingDialogContent(
            configName = configName,
            timerSchedule = timerSchedule,
            nLoop = nLoop,
            isInfinityLoop = isInfinityLoop,
            onConfigNameChange = {
                configName = it
//            configClick.configName = it

            },
            onTimerScheduleChange = {
                timerSchedule = it
//            configClick.timerSchedule = it
            },
            onLoopChange = {
                nLoop = it
//            configClick.nLoop = it
            },
            onInfinityLoopChange = {
                isInfinityLoop = it
//            configClick.isInfinityLoop = it
            },
            onSaveCopyBtnClick = {
                onSettingConfigComponentsChange()
                onInsertConfigClick(configClick.copy(id = -1))
            },
            onUpdateBtnClick = {
                onSettingConfigComponentsChange()
                onUpdateConfigClick(configClick)
            },
            onEarlyClickChange = { onUpdateEarlyClick(it) },
            onDismiss = onDismiss,
        )

}

@ExperimentalComposeUiApi
@Composable
fun SettingDialogContent(
    configName: String,
    onConfigNameChange: (String) -> Unit = {},
    timerSchedule: TimerSchedule,
    onTimerScheduleChange: (TimerSchedule) -> Unit = {},
    nLoop: Int,
    onLoopChange: (Int) -> Unit = {},
    isInfinityLoop: Boolean,
    onInfinityLoopChange: (Boolean) -> Unit = {},
    onDismiss: () -> Unit = {},
    onSaveCopyBtnClick: () -> Unit = { },
    onUpdateBtnClick: () -> Unit = {},
    onEarlyClickChange: (Long) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
//    AutoClickerTheme {

    Column(
        Modifier
            .padding(5.dp)
            .shadow(elevation = 5.dp, shape = MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Cài đặt cấu hình",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        ConfigNameEditable(
            title = "Tên",
            value = configName,
            onValueChange = onConfigNameChange
        )

        TimeScheduleEditable(timerSchedule, onTimerScheduleChange, onEarlyClickChange)

        Spacer(modifier = Modifier.height(10.dp))
        LoopSetting(isInfinityLoop, nLoop, onInfinityLoopChange, onLoopChange)
        DividerAlpha10()

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.align(Alignment.End)) {
            Button(onClick = {
                onSaveCopyBtnClick()
                onDismiss()
            }) {
                Text(text = "Lưu cấu hình")
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "Thoát")
            }

            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = {
                onUpdateBtnClick()
                onDismiss()
            }) {
                Text(text = "Cập nhật")
            }

        }
    }

}

@Composable
private fun TimeScheduleEditable(
    timerSchedule: TimerSchedule,
    onTimerScheduleChange: (TimerSchedule) -> Unit = {},
    onEarlyClickChange: (Long) -> Unit,
) {
    Row {
        Text(
            text = "Hẹn giờ",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
        Checkbox(
            checked = timerSchedule.isTimer,
            onCheckedChange = { onTimerScheduleChange(timerSchedule.copy(isTimer = it)) },
//            modifier = Modifier.align(Alignment.Top)
        )
    }

//    var timeHmsMs = longToHms(timerSchedule.timeMs)

    TimeEditable(
        modifier = Modifier.padding(start = 24.dp),
        timeHmsMs = longToHmsMs(timerSchedule.timeMs)
    ) { onTimerScheduleChange(timerSchedule.copy(timeMs = it.toLongMs())) }

    Spacer(modifier = Modifier.height(10.dp))

    if (!timerSchedule.isTimer) {
        Text(
            text = "Chưa hẹn giờ!",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            modifier = Modifier.padding(start = 24.dp),
        )
    }


    EarlyClickEditable(
        value = timerSchedule.earlyClick
    ) {
        onEarlyClickChange(it)
        onTimerScheduleChange(timerSchedule.copy(earlyClick = it))
    }

    Spacer(modifier = Modifier.height(10.dp))

    if (timerSchedule.isTimer) {
        Text(
            text = "Click bắt đầu lúc: ${longToHmsMs((timerSchedule.timeMs - timerSchedule.earlyClick)).stringFormatHmsMs()}",
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }

    DividerAlpha10()
}

@Composable
private fun TimeEditable(
    modifier: Modifier,
    timeHmsMs: HmsMs,
    onTimeHmsChange: (HmsMs) -> Unit,
) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        BasicTextFieldUnderlineUnit(
            modifier = Modifier.width(80.dp),
            value = timeHmsMs.h,
            valueUnit = "hh",
            onValueChange = {
                onTimeHmsChange(timeHmsMs.copy(h = it))
            },
        )

        BasicTextFieldUnderlineUnit(
            modifier = Modifier.width(80.dp),
            value = timeHmsMs.m,
            valueUnit = "mm",
            onValueChange = {
                onTimeHmsChange(timeHmsMs.copy(m = it))
            },
        )

        BasicTextFieldUnderlineUnit(
            modifier = Modifier.width(80.dp),
            value = timeHmsMs.s,
            valueUnit = "ss",
            onValueChange = {
                onTimeHmsChange(timeHmsMs.copy(s = it))
            },
        )
    }
}

@Composable
private fun EarlyClickEditable(
    value: Long,
    onValueChange: (Long) -> Unit = { }
) {

    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Click sớm",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(modifier = Modifier.height(8.dp))

    BasicTextFieldUnderlineUnit(
        modifier = Modifier
            .padding(start = 24.dp)
            .width(180.dp),
        value = value,
        valueUnit = stringResource(R.string.millisecond),
    ) { onValueChange(it) }
}

@Composable
public fun ColumnScope.ConfigNameEditable(
    title: String,
    value: String,
    onValueChange: (String) -> Unit = { },
) {
    Spacer(Modifier.height(10.dp))
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )

    Spacer(modifier = Modifier.height(4.dp))

    Row(
        modifier = Modifier.padding(start = 18.dp)
    ) {
        BasicTextFieldUnderline(
            modifier = Modifier.fillMaxWidth(0.6f),
            value = value,
            keyboardType = KeyboardType.Text,
            onValueChange = onValueChange,
        )


        /*Column(Modifier.fillMaxWidth(0.6f)) {
            BasicTextFieldUnderline(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                keyboardType = KeyboardType.Text,
                onValueChange = onValueChange,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tên hiện tại: $value",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )

        }
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { *//*TODO*//* }) {
            Text(text = "Lưu")
        }*/

    }

    Spacer(Modifier.height(10.dp))
    DividerAlpha10()


}


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun PreSettingDialogContent() {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        val configClick = ConfigClick()
        SettingDialogContent(
            configName = configClick.configName,
            timerSchedule = configClick.timerSchedule,
            nLoop = configClick.nLoop,
            isInfinityLoop = configClick.isInfinityLoop,
        )
    }
}