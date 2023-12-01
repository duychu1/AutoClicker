package com.duycomp.autoclicker.feature.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duycomp.autoclicker.R
import com.duycomp.autoclicker.model.DarkThemeConfig
import com.duycomp.autoclicker.model.UserData
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.userDataUiState.collectAsStateWithLifecycle()
    val isOverlaying by viewModel.isOverlaying.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState(), enabled = true)
    ) {
        when (uiState) {
            is UserDataUiState.Loading -> {
                Text(
                    text = stringResource(R.string.loading),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            is UserDataUiState.Success -> {
                HomeScreenContent(
                    userData = (uiState as UserDataUiState.Success).userData,
                    onDurationClickChange = viewModel::onDurationClickChange,
                    onIntervalClickChange = viewModel::onIntervalClickChange,
                    onInfinityLoopChange = viewModel::onInfinityLoopChange,
                    onLoopChange = viewModel::onLoopChange,
                    onStartOverlayButtonClick = viewModel::onStartButtonClick,
                    isOverlayController = isOverlaying
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColumnScope.HomeScreenContent(
    userData: UserData,
    onIntervalClickChange: (Long) -> Unit = { },
    onDurationClickChange: (Long) -> Unit = { },
    onInfinityLoopChange: (Boolean) -> Unit = { },
    onLoopChange: (Int) -> Unit = { },
    onStartOverlayButtonClick: (Context) -> Unit = { },
    isOverlayController: Boolean = false,
) {
//    BannerAds()

    Text(
        text = stringResource(R.string.common_setting),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(15.dp)
    )
    {
        val keyboardController = LocalSoftwareKeyboardController.current

        InputItem(
            title = stringResource(R.string.interval_click_title),
            value = userData.intervalClick,
            onValueChange = onIntervalClickChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        DividerAlpha10()

        Spacer(modifier = Modifier.height(20.dp))

        InputItem(
            title = stringResource(R.string.duration_click_title),
            value = userData.durationClick,
            onValueChange = onDurationClickChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        DividerAlpha10()

        Spacer(modifier = Modifier.height(20.dp))

        LoopSetting(userData.isInfinityLoop, userData.nLoop, onInfinityLoopChange, onLoopChange)
    }

    val launchSomeActivity =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                //val data: Intent? = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:com.ruicomp.autoclicker.autocham"))
                // your operation...
            }
        }

    Spacer(modifier = Modifier.height(15.dp))

    val context = LocalContext.current

    Button(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.4f),
        onClick = {
            onStartOverlayButtonClick(context)
        }
    ) {
        if (isOverlayController) Text(text = "Kết thúc", fontSize = 16.sp)
        else Text(text = "Bắt đầu", fontSize = 16.sp)
    }
//    StartButton(context, onStartOverlay, launchSomeActivity)
//    CloseButton(onCloseOverlay)

    Spacer(modifier = Modifier.height(15.dp))

    Column(modifier = Modifier.fillMaxWidth())
    {
        ButtonCustom(text = stringResource(R.string.tutorial))

        Spacer(modifier = Modifier.height(10.dp))

        ButtonCustom(text = stringResource(R.string.response))
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Version: 4.6",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
    )
}

@Composable
fun DividerAlpha10() {
    Divider(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f),
        thickness = 1.dp
    )
}

@Composable
private fun ColumnScope.CloseButton(onCloseOverlay: () -> Unit) {
    Button(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.4f),
        onClick = {
            onCloseOverlay()
        },

        ) {
        Text(text = "Kết thúc")
    }
}

@Composable
private fun ColumnScope.StartButton(
    context: Context,
    onOverlay: (Context) -> Unit,
    launchSomeActivity: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    Button(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.4f),
        onClick = {
            onOverlay(context)
        }
    ) {
        Text(text = "Bắt đầu", fontSize = 16.sp)
    }
}

@Composable
fun LoopSetting(
    isInfinityLoop: Boolean,
    nLoop: Int,
    onInfinityLoopChange: (Boolean) -> Unit,
    onLoopChange: (Int) -> Unit
) {
    Text(
        text = stringResource(R.string.loop_title),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(5.dp))

    // nLoop
    Row(
        Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = !isInfinityLoop,
            onCheckedChange = { onInfinityLoopChange(false) },

            )

        BasicTextFieldUnderlineUnit(
            modifier = Modifier
                .width(100.dp)
                .padding(
                    start = 10.dp,
                    bottom = 3.dp,
                    end = 10.dp
                ),
            value = nLoop,
            valueUnit = stringResource(R.string.n_loop_description),
            onValueChange = { onLoopChange(it) },
        )
    }

//    Spacer(modifier = Modifier.height(1.dp))

    //infinityLoop
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Checkbox(
            checked = isInfinityLoop,
            onCheckedChange = { onInfinityLoopChange(true) }
        )

        Text(
            text = stringResource(R.string.infinity_loop_description),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
public fun ColumnScope.InputItem(
    title: String,
    value: Long,
    onValueChange: (Long) -> Unit,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
    )

    Spacer(modifier = Modifier.height(8.dp))

    BasicTextFieldUnderlineUnit(
        modifier = Modifier.padding(start = 24.dp).fillMaxWidth(0.7f),
        value = value,
        valueUnit = stringResource(R.string.milisecond),
    ) { onValueChange(it) }

}

@Composable
fun BasicTextFieldUnderlineUnit(
    modifier: Modifier,
    value: Long,
    valueUnit: String,
    onValueChange: (Long) -> Unit = {},
) {
    var valueState by remember { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier
    ) {

        BasicTextFieldUnderline(
            modifier = Modifier.weight(1f),
            value = valueState,
            onValueChange = {
                valueState = it
                try {
                    onValueChange(it.toLong())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
        )

        Text(
            text = valueUnit,
            Modifier
                .align(Alignment.Bottom)
                .padding(horizontal = 5.dp)
        )
    }
}

@Composable
fun BasicTextFieldUnderlineUnit(
    modifier: Modifier,
    value: Int,
    valueUnit: String,
    onValueChange: (Int) -> Unit = {},
) {
    var valueState by remember { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier
    ) {

        BasicTextFieldUnderline(
            modifier = Modifier.weight(1f),
            value = valueState,
            onValueChange = {
                valueState = it
                try {
                    onValueChange(it.toInt())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
        )

        Text(
            text = valueUnit,
            Modifier
                .align(Alignment.Bottom)
                .padding(horizontal = 5.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicTextFieldUnderline(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

//    var valueState by remember {
//        mutableStateOf(value)
//    }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.4f),
            thickness = 1.dp
        )
    }

}

@Composable
private fun ButtonCustom(
    text: String,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun HomePreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { Text("AutoClicker") }
            )

            HomeScreenContent(userData = fakeUserData)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomePreviewDark() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { Text("AutoClicker") }
            )

            HomeScreenContent(userData = fakeUserData)
        }
    }
}

val fakeUserData = UserData(
    darkThemeConfig = DarkThemeConfig.DARK,
    useDynamicColor = false,
)