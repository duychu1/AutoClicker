package com.duycomp.autoclicker.feature.overlay.clock

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duycomp.autoclicker.feature.overlay.target.actingLikeLifecycle
import com.duycomp.autoclicker.ui.theme.clockBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

var clockOffset = 0

@SuppressLint("SimpleDateFormat")
fun clockView(
    context: Context,
): ComposeView {
    val composeView = ComposeView(context)
    composeView.setContent {
        ClockContent()
    }

    composeView.actingLikeLifecycle()

    return composeView
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ClockContent(
    viewModel: ClockViewModel = hiltViewModel()
) {
    val time by viewModel.clock.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .padding(5.dp)
            .shadow(elevation = 5.dp, shape = MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large)
    ) {
        Text(
            text = time,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(clockBackground)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.large
                )
                .padding(vertical = 6.dp, horizontal = 16.dp)
        )
    }

//    LaunchedEffect(key1 = Unit) {
//        withContext(Dispatchers.IO) {
//            processingTime(time)
//        }
//    }
}

@SuppressLint("SimpleDateFormat")
private suspend fun processingTime(time: MutableState<String>) {
    var preCurrent: Long = getCurrentTime() - clockOffset
    var finalResetTime = preCurrent
    var timeOld = getCurrentTime()
    while (true) {
        withContext(Dispatchers.Main) {
            time.value = SimpleDateFormat("HH:mm:ss.S").format(preCurrent)
        }
        delay(100)
        val calculateTime = getCurrentTime() - timeOld
        preCurrent += calculateTime
        timeOld = getCurrentTime()
//        Log.d("TAG", "processingTime: ${Thread.currentThread().name}")

//        if(preCurrent - finalResetTime > 30000) {
//            if(!isPlay) {
//                CoroutineScope(Dispatchers.IO).launch {
//                    clockOffset = getDeltaTime()
//                }
//            }
//            preCurrent = getCurrentTime() - clockOffset
//            finalResetTime = preCurrent
//        }
    }
}

