package com.duycomp.autoclicker.feature.overlay.clock

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
fun ClockContent() {
    val time = remember { mutableStateOf("") }

    Text(
        text = time.value,
        fontWeight = FontWeight.Medium,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(clockBackground)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large
            )
            .padding(vertical = 15.dp, horizontal = 24.dp)
    )

    LaunchedEffect(key1 = Unit) {
        withContext(Dispatchers.IO) {
            processingTime(time)
        }
    }
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
//            if(!isplay) {
//                CoroutineScope(Dispatchers.IO).launch {
//                    saiso = getDeltaTime()
//                }
//            }
//            preCurrent = getCurentTime() - saiso
//            finalResetTime = preCurrent
//        }
    }
}

fun getCurrentTime(): Long = System.currentTimeMillis()