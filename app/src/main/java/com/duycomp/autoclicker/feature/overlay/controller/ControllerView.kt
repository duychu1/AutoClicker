package com.duycomp.autoclicker.feature.overlay.controller

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duycomp.autoclicker.feature.overlay.target.composeToView
import com.duycomp.autoclicker.ui.theme.AcIcons

@Composable
fun ControllerCompose(
    viewModel: ControllerViewModel = hiltViewModel(),
) {

    ControllerContent(
        isPlay = viewModel.isPlaying,
        onPlayClick = viewModel::onPlayClick ,
        onAddClick = viewModel::onAddClick ,
        onRemoveClick = viewModel::onRemoveClick ,
        onSettingClick = viewModel::onSettingClick ,
        onClockClick = viewModel::onClockClick ,
        onFolderClick = viewModel::onFolderClick ,
    )
}

@Composable
private fun ControllerContent(
    isPlay: Boolean = false,
    onPlayClick: (Boolean) -> Unit = { },
    onAddClick: (Context) -> Unit = { },
    onRemoveClick: () -> Unit = { },
    onSettingClick: (Context) -> Unit = { },
    onClockClick: () -> Unit = { },
    onFolderClick: () -> Unit = { },
) {
    Column(
        modifier = Modifier
            .padding(2.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.9f))
    ) {
        val context = LocalContext.current

        if (isPlay) IconButtonItem(AcIcons.pauseCircle) {
            onPlayClick(false)
        } else IconButtonItem(imageVector = AcIcons.playCircle) {
            onPlayClick(true)
        }

        IconButtonItem(imageVector = AcIcons.add, onClick = { onAddClick(context) })
        IconButtonItem(imageVector = AcIcons.remove, onClick = onRemoveClick)
        IconButtonItem(imageVector = AcIcons.setting, onClick = { onSettingClick(context) })
        IconButtonItem(imageVector = AcIcons.time, onClick = onClockClick)
        IconButtonItem(imageVector = AcIcons.folder, onClick = onFolderClick)
        IconButtonItemNonClick(imageVector = AcIcons.move)
//        IconButtonItem(imageVector = AcIcons.close, )

    }
}

@Composable
private fun IconButtonItem(
    imageVector: ImageVector,
    onClick: () -> Unit = { },
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(45.dp)
            .padding(3.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun IconButtonItemNonClick(imageVector: ImageVector) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(45.dp)
            .padding(3.dp)
            .clip(MaterialTheme.shapes.medium)

    )
}

fun controllerView(context: Context) : ComposeView =
    composeToView(context = context, content = { ControllerCompose() })

@Preview
@Composable
fun PreController() {
    ControllerContent()
}