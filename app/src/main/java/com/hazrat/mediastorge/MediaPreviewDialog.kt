package com.hazrat.mediastorge

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage

@Composable
fun MediaPreviewDialog(
    file: MediaFile,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = file.name) },
        text = {
            when (file.type) {
                MediaType.IMAGE -> {
                    AsyncImage(
                        model = file.uri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                MediaType.VIDEO, MediaType.AUDIO -> {
                    Text("Click Play to open in default media player")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                when (file.type) {
                    MediaType.VIDEO, MediaType.AUDIO -> {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(Uri.parse(file.uri.toString()), when(file.type) {
                                MediaType.VIDEO -> "video/*"
                                MediaType.AUDIO -> "audio/*"
                                else -> "*/*"
                            })
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(intent)
                    }

                    MediaType.IMAGE -> {}
                }
                onDismiss()
            }) {
                Text(if (file.type == MediaType.IMAGE) "Close" else "Play")
            }
        }
    )
}