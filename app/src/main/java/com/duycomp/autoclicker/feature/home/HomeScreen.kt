package com.duycomp.autoclicker.feature.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
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
import com.duycomp.autoclicker.ui.theme.card
import com.duycomp.autoclicker.ui.theme.dividerSpace
import com.duycomp.autoclicker.ui.theme.inputLine
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.userDataUiState.collectAsStateWithLifecycle()

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
            .background(card)
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

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = dividerSpace,
            thickness = 1.dp

        )

        Spacer(modifier = Modifier.height(20.dp))

        InputItem(
            title = stringResource(R.string.duration_click_title),
            value = userData.durationClick,
            onValueChange = onDurationClickChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = dividerSpace,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(20.dp))

        LoopSetting(userData, onInfinityLoopChange, onLoopChange)
    }

    val launchSomeActivity =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                //val data: Intent? = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:com.ruicomp.autoclicker.autocham"))
                // your operation...
            }
        }

    Spacer(modifier = Modifier.height(15.dp))

    Button(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.4f),
        onClick = { }
    ) {
        Text(text = "Bắt đầu", fontSize = 16.sp)
    }

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
private fun LoopSetting(
    userData: UserData,
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
        Modifier.padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = !userData.isInfinityLoop,
            onCheckedChange = { onInfinityLoopChange(false) },

            )
        Column(
            Modifier.padding(
                start = 10.dp,
                bottom = 3.dp,
                end = 10.dp
            )
        ) {
            TextFieldCustom(
                modifier = Modifier.width(60.dp),
                value = userData.nLoop.toString(),
                onValueChange = { onLoopChange(it.toInt()) },
            )

            Divider(
                modifier = Modifier
                    .width(60.dp),
                color = inputLine,
                thickness = 1.dp
            )

        }

        Text(text = stringResource(R.string.n_loop_description), Modifier.padding(bottom = 3.dp))
    }

    Spacer(modifier = Modifier.height(1.dp))

    //infinityLoop
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(start = 10.dp)
    ) {
        Checkbox(
            checked = userData.isInfinityLoop,
            onCheckedChange = { onInfinityLoopChange(true) }
        )

        Text(
            text = stringResource(R.string.infinity_loop_description),
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun ColumnScope.InputItem(
    title: String,
    value: Long,
    onValueChange: (Long) -> Unit,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row {
        Column(Modifier.fillMaxWidth(0.6f)) {
            TextFieldCustom(
                modifier = Modifier.fillMaxWidth(),
                value = value.toString(),
                onValueChange = { onValueChange(it.toLong()) },
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp),
                color = inputLine,
                thickness = 1.dp
            )
        }

        Text(text = stringResource(R.string.milisecond), Modifier.padding(start = 5.dp))
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
            .background(card)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TextFieldCustom(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = TextStyle(
            fontSize = 17.sp,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    Surface(Modifier.fillMaxSize()) {
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