package com.duycomp.autoclicker.feature.overlay.setting

import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.duycomp.autoclicker.model.longToHms
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalComposeUiApi
fun settingDialogView(
    context: Context,
    windowManager: WindowManager,
    configClick: MutableStateFlow<ConfigClick>,
    userDataRepository: UserDataRepositoryImpl,
    configDatabaseRepository: ClickerConfigDatabaseRepositoryImpl,
) :ComposeView {
    val composeView = ComposeView(context) 
    composeView.setContent { 
        SettingDialog(
            viewModel = SettingDialogViewModel(
                _configClick = configClick,
                userDataRepository = userDataRepository,
                configDatabaseRepository = configDatabaseRepository,
                windowManager = windowManager,
                composeView = composeView
            )
        )
    }
    
    composeView.actingLikeLifecycle()

    return composeView
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingDialog(
    viewModel: SettingDialogViewModel
) {
    val configClick by viewModel.configClick.collectAsStateWithLifecycle()

    SettingDialogContent(
        configName = configClick.configName,
        timerSchedule = configClick.timerSchedule,
        nLoop = configClick.nLoop,
        isInfinityLoop = configClick.isInfinityLoop,
        onConfigNameChange = viewModel::onConfigNameChange,
        onTimerScheduleChange = viewModel::onTimerScheduleChange,
        onLoopChange = viewModel::onLoopChange,
        onInfinityLoopChange = viewModel::onInfinityLoopChange,
        onDismiss = viewModel::onDismiss,
    )
}

@ExperimentalComposeUiApi
@Composable
fun SettingDialogContent(
    configName: String,
    onConfigNameChange: (String) -> Unit = {},
    timerSchedule: TimerSchedule,
    onTimerScheduleChange: (TimerSchedule) -> Unit ={},
    nLoop: Int,
    onLoopChange: (Int) -> Unit = {},
    isInfinityLoop: Boolean,
    onInfinityLoopChange: (Boolean) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        Modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight(0.6f)
            .padding(18.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Cài đặt cấu hình",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        ConfigNameEditable(title = "Tên", value = configName, onValueChange = onConfigNameChange)

        TimeScheduleEditable(timerSchedule, onTimerScheduleChange)

        Spacer(modifier = Modifier.height(10.dp))
        LoopSetting(isInfinityLoop, nLoop, onInfinityLoopChange, onLoopChange)
        DividerAlpha10()

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.align(Alignment.End)) {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "Thoát")
            }

            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Đồng ý")
            }

        }
    }
}

@Composable
private fun TimeScheduleEditable(
    timerSchedule: TimerSchedule,
    onTimerScheduleChange: (TimerSchedule) -> Unit = {},
) {
    Row {
        Text(
            text = "Hẹn giờ",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterVertically)
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
        timeHmsMs = longToHms(timerSchedule.timeMs)
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
        onTimerScheduleChange(timerSchedule.copy(earlyClick = it))
    }

    Spacer(modifier = Modifier.height(10.dp))

    if (timerSchedule.isTimer) {
        Text(
            text = "Click bắt đầu lúc: ${longToHms((timerSchedule.timeMs - timerSchedule.earlyClick)).toHHmmssSSS()}",
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
    )
    Spacer(modifier = Modifier.height(8.dp))

    BasicTextFieldUnderlineUnit(
        modifier = Modifier
            .padding(start = 24.dp)
            .width(180.dp),
        value = value,
        valueUnit = stringResource(R.string.milisecond),
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
    )

    Spacer(modifier = Modifier.height(4.dp))

    Row(
        modifier = Modifier.padding(start = 18.dp)
    ) {
        Column(Modifier.fillMaxWidth(0.6f)) {
            BasicTextFieldUnderline(
                modifier = Modifier.fillMaxWidth(),
                value = value,
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

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Lưu")
        }

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
            .fillMaxHeight()) {

        val configClick = ConfigClick()
        SettingDialogContent(
            configName = configClick.configName,
            timerSchedule = configClick.timerSchedule,
            nLoop = configClick.nLoop,
            isInfinityLoop = configClick.isInfinityLoop,
        )
    }
}