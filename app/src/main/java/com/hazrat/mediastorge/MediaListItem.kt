package com.hazrat.mediastorge

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

/**
 * @author Hazrat Ummar Shaikh
 */

@Composable
fun MediaListItem(
    file: MediaFile,
    modifier: Modifier = Modifier
) {
    var showPreviewDialog by remember {
        mutableStateOf(false)
    }
    if (showPreviewDialog) {
        MediaPreviewDialog(
            file = file,
            onDismiss = { showPreviewDialog = false }
        )
    }
    val context = LocalContext.current
    Row(
        modifier = modifier.clickable { showPreviewDialog = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        when(file.type){
            MediaType.IMAGE -> {
                AsyncImage(
                    model = file.uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                )
            }
            MediaType.VIDEO -> {
                val videoThumbnail = remember(file.uri) { getVideoThumbnail(file.uri) }
                if (videoThumbnail != null) {
                    Image(
                        bitmap = videoThumbnail.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }else{
                    AsyncImage(
                        model = file.uri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.width(100.dp)
                    )
                }
            }
            MediaType.AUDIO -> {
                Image(
                    painter = painterResource(R.drawable.audio_file),
                    contentDescription = null,
                    modifier = Modifier.width(100.dp)
                )
            }
        }
        Spacer(Modifier.width(20.dp))
        Text(
            text = "${file.name} - ${file.type}",
            modifier = Modifier.width(16.dp).weight(1f)
        )
    }
    Spacer(Modifier.height(20.dp))
}

fun getVideoThumbnail(videoUri: Uri): Bitmap?{
    val retriever = MediaMetadataRetriever()
    return  try {
        retriever.setDataSource(videoUri.toString(), HashMap())
        retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    }catch (e:Exception){
        e.printStackTrace()
        null
    }finally {
        retriever.release()
    }
}