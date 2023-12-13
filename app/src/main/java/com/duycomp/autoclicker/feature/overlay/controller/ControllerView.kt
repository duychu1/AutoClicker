package com.duycomp.autoclicker.feature.overlay.controller

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duycomp.autoclicker.feature.overlay.target.actingLikeLifecycle
import com.duycomp.autoclicker.ui.theme.AcIcons
import com.duycomp.autoclicker.ui.theme.controllerIcon

fun controllerView(context: Context): ComposeView {
    val composeView = ComposeView(context)
    composeView.setContent {
        ControllerCompose(composeView)
    }

    composeView.actingLikeLifecycle()

    return composeView
}

@Composable
fun ControllerCompose(
    composeView: ComposeView,
    viewModel: ControllerViewModel = hiltViewModel(),
) {

    ControllerContent(
        isPlay = viewModel.isPlaying,
        onPlayClick = viewModel::onPlayClick,
        onAddClick = viewModel::onAddClick,
        onRemoveClick = viewModel::onRemoveClick,
        onSettingClick = viewModel::onSettingClick,
        onClockClick = viewModel::onClockClick,
        onFolderClick = viewModel::onFolderClick,
        onCloseClick = { viewModel.onCloseClick(it, composeView) }
    )
}

@Composable
private fun ControllerContent(
    isPlay: Boolean = false,
    isHorizontalController: Boolean = false,
    onPlayClick: (Boolean) -> Unit = { },
    onAddClick: (Context) -> Unit = { },
    onRemoveClick: () -> Unit = { },
    onSettingClick: (Context) -> Unit = { },
    onClockClick: (Context) -> Unit = { },
    onFolderClick: (Context) -> Unit = { },
    onCloseClick: (Context) -> Unit = { }
) {
    if (isHorizontalController)
        HorizontalController(
            isPlay,
            onPlayClick,
            onAddClick,
            onRemoveClick,
            onSettingClick,
            onClockClick,
            onFolderClick,
            onCloseClick
        )
    else
        VerticalController(
            isPlay,
            onPlayClick,
            onAddClick,
            onRemoveClick,
            onSettingClick,
            onClockClick,
            onFolderClick,
            onCloseClick
        )
}

@Composable
private fun VerticalController(
    isPlay: Boolean,
    onPlayClick: (Boolean) -> Unit,
    onAddClick: (Context) -> Unit,
    onRemoveClick: () -> Unit,
    onSettingClick: (Context) -> Unit,
    onClockClick: (Context) -> Unit,
    onFolderClick: (Context) -> Unit,
    onCloseClick: (Context) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color.Gray.copy(0.8f))
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
        IconButtonItem(imageVector = AcIcons.time, onClick = { onClockClick(context) })
        IconButtonItem(imageVector = AcIcons.folder, onClick = { onFolderClick(context) })
        IconButtonItemNonClick(imageVector = AcIcons.move)
        IconButtonItem(imageVector = AcIcons.close, onClick = { onCloseClick(context) })

    }
}

@Composable
private fun HorizontalController(
    isPlay: Boolean,
    onPlayClick: (Boolean) -> Unit,
    onAddClick: (Context) -> Unit,
    onRemoveClick: () -> Unit,
    onSettingClick: (Context) -> Unit,
    onClockClick: (Context) -> Unit,
    onFolderClick: (Context) -> Unit,
    onCloseClick: (Context) -> Unit
) {
    Row(
        modifier = Modifier
//            .padding(2.dp)
            .clip(MaterialTheme.shapes.small)
            .background(Color.DarkGray.copy(alpha = 0.3f))
    ) {
        val context = LocalContext.current

        if (isPlay)
            IconButtonItemHor(imageVector = AcIcons.pauseCircle) { onPlayClick(false) }
        else
            IconButtonItemHor(imageVector = AcIcons.playCircle) { onPlayClick(true) }

        IconButtonItemHor(imageVector = AcIcons.add, onClick = { onAddClick(context) })
        IconButtonItemHor(imageVector = AcIcons.remove, onClick = onRemoveClick)
        IconButtonItemHor(imageVector = AcIcons.setting, onClick = { onSettingClick(context) })
        IconButtonItemHor(imageVector = AcIcons.time, onClick = { onClockClick(context) })
        IconButtonItemHor(imageVector = AcIcons.folder, onClick = { onFolderClick(context) })
        IconButtonItemNonClickHor(imageVector = AcIcons.move)
        IconButtonItemHor(imageVector = AcIcons.close, onClick = { onCloseClick(context) })

    }
}

@Composable
private fun IconButtonItem(
    imageVector: ImageVector,
    iconSize: Int = 45,
    onClick: () -> Unit = { },
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = controllerIcon,
        modifier = Modifier
            .size(iconSize.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun IconButtonItemNonClick(
    imageVector: ImageVector,
    iconSize: Int = 45,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = controllerIcon,
        modifier = Modifier
            .size(iconSize.dp)
            .clip(MaterialTheme.shapes.medium)

    )
}

@Composable
fun IconButtonItemHor(
    imageVector: ImageVector,
    iconSize: Int = 40,
    onClick: () -> Unit = { },
) {
    IconButtonItem(imageVector, iconSize, onClick)
}

@Composable
private fun IconButtonItemNonClickHor(
    imageVector: ImageVector,
    iconSize: Int = 40,
) {
    IconButtonItemNonClick(imageVector, iconSize)
}


@Preview
@Composable
fun PreController() {
    ControllerContent()
}