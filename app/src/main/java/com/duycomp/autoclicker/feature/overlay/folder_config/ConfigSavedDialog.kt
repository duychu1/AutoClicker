package com.duycomp.autoclicker.feature.overlay.folder_config

import android.content.Context
import android.view.WindowManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duycomp.autoclicker.R
import com.duycomp.autoclicker.data.ClickerConfigDatabaseRepositoryImpl
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.database.model.ConfigPreview
import com.duycomp.autoclicker.feature.home.DividerAlpha10
import com.duycomp.autoclicker.feature.overlay.target.ManagerTargets
import com.duycomp.autoclicker.feature.overlay.target.actingLikeLifecycle
import com.duycomp.autoclicker.model.ConfigClick
import com.duycomp.autoclicker.ui.theme.AcIcons

fun configSavedDialogView(
    context: Context,
    windowManager: WindowManager,
    configClick: ConfigClick,
    managerTargets: ManagerTargets,
    userDataRepository: UserDataRepositoryImpl,
    configDatabaseRepository: ClickerConfigDatabaseRepositoryImpl,
    onConfigChange: (ConfigClick) -> Unit,
): ComposeView {
    val composeView = ComposeView(context)
    composeView.setContent {
        ConfigSavedDialog(
            configClick = configClick,
            onConfigChange = onConfigChange,
            viewModel = ConfigSavedDialogViewModel(
                composeView = composeView,
                windowManager = windowManager,
                managerTargets = managerTargets,
                userDataRepository = userDataRepository,
                configDatabaseRepository = configDatabaseRepository,

            )
        )
    }

    composeView.actingLikeLifecycle()

    return composeView
}

@Composable
fun ConfigSavedDialog(
    configClick: ConfigClick,
    onConfigChange: (ConfigClick) -> Unit,
    viewModel: ConfigSavedDialogViewModel
) {

    val configState by viewModel.configState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .padding(15.dp)
    ) {
        Text(
            text = "Chọn cấu hình đã lưu",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge
        )

        when (configState) {
            is ConfigUiState.Loading -> {
                Text(text = stringResource(id = R.string.loading))
            }

            is ConfigUiState.Success -> {
                val configs = (configState as ConfigUiState.Success).configsPreview

                if (configs.isEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Hiện tại đang trống",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }

                val deletedItems = remember { mutableStateListOf<ConfigPreview>() }
                LazyColumn(
                    Modifier
                        .weight(1f)
                ) {
                    items(items = configs, key = { it.id }) { item ->
                        AnimatedVisibility(
                            visible = !deletedItems.contains(item),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 400))
                        ) {
                            ConfigDialogItem(
                                text = item.configName,
                                onDelete = {
                                    deletedItems.add(item)
                                    viewModel.onDeleteConfig(item)
                                },
                                onOpen = { viewModel.onOpenConfig(context, item, configClick, onConfigChange) }
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Button(
                onClick = { viewModel.onCreateNewConfig(configClick, onConfigChange) },
            ) {
                Text(text = "Tạo mới")
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { viewModel.onDismiss() }
            ) {
                Text(text = "Thoát")
            }
        }

    }
}

@Composable
fun ConfigDialogItem(
    text: String,
    onDelete: () -> Unit = {},
    onOpen: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 3.dp)
            .clickable { onOpen() }
            .clip(shape = MaterialTheme.shapes.medium),
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 5.dp, top = 15.dp)
        )

        Icon(
            imageVector = AcIcons.delete,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .clickable {
                    onDelete()
                }

        )

        DividerAlpha10()
    }
}

@Preview
@Composable
fun PreItem() {
    Surface {
//        ConfigSavedDialog(configs = fakeConfigPreview)
    }
}

val fakeConfigPreview = listOf(
    ConfigPreview(1, 1, "Cau hinh 1"),
    ConfigPreview(2, 2, "Cau hinh 2"),
    ConfigPreview(3, 3, "Cau hinh 3"),
)