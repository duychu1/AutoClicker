package com.duycomp.autoclicker.feature.home.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duycomp.autoclicker.R
import com.duycomp.autoclicker.ui.theme.AutoClickerTheme

@Composable
fun FeedbackDialog(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Phản hồi",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )

        },

        text = {

            val annotatedLinkString: AnnotatedString = buildAnnotatedString {

                val str: String = stringResource(id = R.string.feedback_text)
                val webStartIndex = str.indexOf("website.")
                val webEndIndex = webStartIndex + 8
                append(str)
                addStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.inversePrimary,
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline
                    ),
                    start = webStartIndex,
                    end = webEndIndex
                )

                // attach a string annotation that stores a URL to the text "link"
                addStringAnnotation(
                    tag = "URL",
                    annotation = "https://forms.gle/XG1yymq2qyxvvqaY7",
                    start = webStartIndex,
                    end = webEndIndex
                )
            }

// UriHandler parse and opens URI inside AnnotatedString Item in Browse
            val uriHandler = LocalUriHandler.current

// 🔥 Clickable text returns position of text that is clicked in onClick callback
            ClickableText(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = annotatedLinkString,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                ),
                onClick = {
                    annotatedLinkString
                        .getStringAnnotations("URL", it, it)
                        .firstOrNull()?.let { stringAnnotation ->
                            uriHandler.openUri(stringAnnotation.item)
                        }
                }
            )

        },

        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "Thoát")
            }
        }
    )
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class DayNightPreview

@DayNightPreview
@Composable
fun prevFeedbackDialog() {
    AutoClickerTheme {
        FeedbackDialog {

        }
    }
}