package com.arjixwastaken.nyaadroid.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arjixwastaken.nyaaapi.models.TorrentPreview

@Composable
fun TorrentItem(torrent: TorrentPreview) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Gray)
        .padding(start = 15.dp, end = 15.dp)) {
        Row {
            Text(torrent.category, modifier = Modifier.padding(15.dp))
            Text(torrent.title, fontSize = 15.sp)
            // TODO: Actually make this pretty and more informative.
        }
    }
}