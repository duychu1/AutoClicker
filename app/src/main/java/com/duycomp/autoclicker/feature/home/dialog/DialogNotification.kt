package com.duycomp.autoclicker.feature.home.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.duycomp.autoclicker.R

@Composable
fun DialogNotification(
    title: String = stringResource(R.string.accessibility_notify_title),
    description: String,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    AlertDialog(onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },

        text = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(top = 20.dp)
                )
            }

        },

        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Đồng ý")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Thoát")
            }
        }


    )
}

@Preview
@Composable
fun PreDialogNotification() {
    DialogNotification(
        description = stringResource(R.string.accessibility_notify_description)
    )
}
