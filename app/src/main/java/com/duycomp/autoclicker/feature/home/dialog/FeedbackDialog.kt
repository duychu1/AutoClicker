package com.duycomp.autoclicker.feature.home.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duycomp.autoclicker.R

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

//            Column(Modifier.fillMaxWidth()) {
//                Spacer(modifier = Modifier.height(3.dp))
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(MaterialTheme.shapes.medium)
//                        .background(Color.LightGray),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        // https://forms.gle/XG1yymq2qyxvvqaY7
//                        //https://www.facebook.com/276536540874604/posts/276539017541023
//                        text = "Phản hồi",
//                        textAlign = TextAlign.Center,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp,
//                        color = Purple700,
//                        modifier = Modifier.padding(5.dp)
//                    )
//                }
//
//                Text(text = "", Modifier.height(1.dp))
//            }


        },

        text = {
//            Box(
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "",
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 17.sp,
//                    modifier = Modifier.padding(top = 20.dp)
//                )
//            }


            val annotatedLinkString: AnnotatedString = buildAnnotatedString {

                val str: String = stringResource(id = R.string.feedback_text)
                val webStartIndex = str.indexOf("website.")
                val webEndIndex = webStartIndex + 8
//                val fbStartIndex  = str.indexOf("này trên")
//                val fbEndIndext   = fbStartIndex + 3
                append(str)
                addStyle(
                    style = SpanStyle(
                        color = Color.Blue,
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

//                addStyle(
//                    style = SpanStyle(
//                        color = hyperlink,
//                        fontSize = 17.sp,
//                        textDecoration = TextDecoration.Underline
//                    ),
//                    start = fbStartIndex,
//                    end = fbEndIndext
//                )
//
//                addStringAnnotation(
//                    tag = "URL",
//                    annotation = "https://www.facebook.com/276536540874604/posts/276539017541023",
//                    start = fbStartIndex,
//                    end = fbEndIndext
//                )


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
//            Column(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Button(
//                    onClick = {
//                        showFeedback.value = false
//                    },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = exitButton),
//                    elevation = ButtonDefaults.elevation(
//                        defaultElevation = 0.dp,
//                        pressedElevation = 4.dp
//                    )
//                ) {
//                    Text(text = "Thoát", fontSize = 16.sp)
//                }
//            }

        },

//        dismissButton = {
//            Button(
//                colors = ButtonDefaults.buttonColors(backgroundColor = exitButton),
//                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 4.dp),
//                onClick = {
//                    showFeedback.value = false
//                }) {
//                Text(text = "Thoát", fontSize = 16.sp)
//            }
//        }


    )
}